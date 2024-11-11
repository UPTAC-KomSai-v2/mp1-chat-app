package main.server;

import main.models.User;
import java.sql.SQLException;

// class for user registration
public class UserRegistration {
    private UserRegistrationVerification userRegistrationVerification;

    public UserRegistration() {
        this.userRegistrationVerification = new UserRegistrationVerification();
    }

    // REMOVE USER_ID AFTER DECISION ON HOW TO HANDLE IT (e.g., make auto-increment)
    public String registerUser(int user_id, String username, String email, String password_hash) throws SQLException {
        User user = new User(user_id, username, email, password_hash);
        return userRegistrationVerification.registerUser(user);
    }
}
