package main.server;

import main.models.User;
import main.utils.DatabaseUtils;
import org.mindrot.jbcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;

@RestController
@RequestMapping("/api/login")
public class UserLoginController {

    // Secret key for signing JWTs (store securely in environment variables)
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String emailOrUsername = loginRequest.get("emailOrUsername");
        String password = loginRequest.get("password");

        try {
            // Find user by email or username
            User user = findUserByEmailOrUsername(emailOrUsername);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not registered.");
            }

            // Verify the password using BCrypt
            if (!BCrypt.checkpw(password, user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
            }

            // Generate JWT token
            String token = generateJwtToken(user);

            // Return the token and user details
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                    "user_id", user.getUserID(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "profile_picture", user.getProfilePicture() != null ? user.getProfilePicture() : "default.jpg",
                    "role", user.getRole(),
                    "last_login", user.getLastLogin() != null ? user.getLastLogin() : "Not available"
            ));
            response.put("message", "Login successful.");

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error.");
        }
    }

    // Method to find user by email or username and update last login
    private User findUserByEmailOrUsername(String emailOrUsername) throws SQLException {
        String query = "SELECT * FROM Users WHERE email = ? OR username = ?";
        String updateLoginQuery = "UPDATE Users SET last_login = ? WHERE user_id = ?";

        try (Connection conn = DatabaseUtils.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, emailOrUsername);
                statement.setString(2, emailOrUsername);

                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    User user = new User();
                    user.setUserID(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setProfilePicture(rs.getString("profile_picture"));
                    user.setRole(rs.getString("role"));
                    user.setLastLogin(rs.getString("last_login"));

                    // Update the last login time after successful login
                    try (PreparedStatement updateStatement = conn.prepareStatement(updateLoginQuery)) {
                        updateStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                        updateStatement.setInt(2, user.getUserID());
                        updateStatement.executeUpdate();
                    }

                    // Refresh the user object with updated last_login value
                    try (PreparedStatement refreshStatement = conn.prepareStatement(query)) {
                        refreshStatement.setString(1, emailOrUsername);
                        refreshStatement.setString(2, emailOrUsername);
                        ResultSet refreshedRs = refreshStatement.executeQuery();
                        if (refreshedRs.next()) {
                            user.setLastLogin(refreshedRs.getString("last_login"));
                        }
                    }

                    return user;
                }
            }
        }
        return null;
    }


    // Generate JWT
    private String generateJwtToken(User user) {
        long expirationTime = 1000 * 60 * 60 * 24; // 24 hours

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getUserID())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}