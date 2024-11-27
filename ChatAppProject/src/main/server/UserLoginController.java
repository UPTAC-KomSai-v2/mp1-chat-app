package main.server;

import main.models.User;
import main.utils.DatabaseUtils;
import org.mindrot.jbcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class UserLoginController {

    // Secret key for signing JWTs (store securely in environment variables)
    private static final String SECRET_KEY = "k83JH+jsT7MqVfQrKm8P5J7qT+Ub43r8HoM9Fw==";
    private static final Key SIGNING_KEY = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String emailOrUsername = loginRequest.get("emailOrUsername");
        String password = loginRequest.get("password");

        try {
            // Find user by email or username
            User user = findUserByEmailOrUsername(emailOrUsername);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }

            // Verify the password using BCrypt
            if (!BCrypt.checkpw(password, user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
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
                    "profile_picture", user.getProfilePicture(),
                    "role", user.getRole(),
                    "last_login", user.getLastLogin()
            ));
            response.put("message", "Login successful.");

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error.");
        }
    }

    // Find user by email or username
    private User findUserByEmailOrUsername(String emailOrUsername) throws SQLException {
        String query = "SELECT * FROM Users WHERE email = ? OR username = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
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
                return user;
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