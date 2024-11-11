package main.server;

import main.models.User;
import main.utils.DatabaseUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

// class for validating and verifying user registration before inserting to database
public class UserRegistrationVerification {
    // regex pattern for email with format like a@email.com
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    // regex pattern for password with at least 1 lowercase, uppercase, and digit; and minimum of 8 chars.
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    // Check if email is valid based on regex
    public boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Check if email is unique (not in the database)
    public boolean isEmailUnique(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt(1)>0;
            }
        }

        return false;
    }

    // Check if password is valid based on regex
    public boolean isPasswordValid(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // Hash password using BCrypt
    public String hashPassword (String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
        // verify login with BCrypt.checkpw(plainPassword, hashedPassword)
    }

    public String registerUser(User user) throws SQLException {
        // Validate email and password
        if(!isEmailValid(user.getEmail())) {
            return "Invalid email format.";
        }

        if(isEmailUnique(user.getEmail())) {
            return "Email already in use.";
        }

        if(!isPasswordValid(user.getPasswordHash())) {
            return "Weak password.";
        }

        // Hash Password
        String password_hash = hashPassword(user.getPasswordHash());
        user.setPasswordHash(password_hash);
        if (password_hash == null) {
            return "Failed to hash password.";
        }

        // Store the user information, then return success or failure message
        // MODIFY QUERY AND STATEMENT: REMOVE USER_ID AFTER DECISION ON HOW TO HANDLE IT,
        // THEN CHANGE PARAMETERINDEX FOR EACH STATEMENT BELOW
        String query = "INSERT INTO users (user_id, username, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection()) {
            conn.setAutoCommit(false);  // Disable auto-commit to start a transaction

            try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, user.getUserID());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPasswordHash());
                statement.setString(5, "user");     // initialize role = "user"

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 1) {
                    conn.commit();  // Enable commit after successful transaction
                    return "Successfully registered user.";
                } else {
                    conn.rollback(); // Rollback after failed transaction
                    return "Failed to register user. Database error.";
                }
            } catch (SQLException e) {
                conn.rollback(); // Rollback after failed transaction
                e.printStackTrace();
                return "Failed to register user. Database error.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to register user. Cannot connect to database.";
        }
    }

}