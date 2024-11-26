package main.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.utils.DatabaseUtils;

public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private String profilePicture;
    private Timestamp lastLogin;

    // Constructor
    public User(int userId, String username, String email, String passwordHash, String profilePicture, Timestamp lastLogin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.profilePicture = profilePicture;
        this.lastLogin = lastLogin;
    }

    public User() {
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<String[]> getChannelsWithRoles() throws SQLException {
        String query = """
        SELECT c.channel_id, c.channel_name, c.is_private,
        COALESCE(cm.role, 'user') AS role, ? AS user_id
         FROM Channels c
         LEFT JOIN Channel_Managers cm ON c.channel_id = cm.channel_id AND cm.user_id = ?
         LEFT JOIN Channel_Members mem ON c.channel_id = mem.channel_id AND mem.user_id = ?
         WHERE c.channel_id IN (
             SELECT cm.channel_id FROM Channel_Managers cm WHERE cm.user_id = ?
         )
         OR c.channel_id IN (
             SELECT mem.channel_id FROM Channel_Members mem WHERE mem.user_id = ?
         );
                
        """;

        List<String[]> result = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the user ID for all placeholders
            stmt.setInt(1, this.userId);  // For the `user_id` in the select clause
            stmt.setInt(2, this.userId);  // For `cm.user_id`
            stmt.setInt(3, this.userId);  // For `mem.user_id`
            stmt.setInt(4, this.userId);  // For `mem.user_id`
            stmt.setInt(5, this.userId);  // For the `user_id` in the select clause

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String channelId = String.valueOf(rs.getInt("channel_id"));
                String channelName = rs.getString("channel_name");
                String isPrivate = rs.getBoolean("is_private") ? "true" : "false";
                String userID = rs.getString("user_id");
                String role = rs.getString("role");

                // If no specific role is found, set it to "user"
                if (role == null || role.isEmpty()) {
                    role = "user";
                }
                result.add(new String[]{channelId, channelName, isPrivate, userID, role});
            }
        }
        return result;
    }

    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT user_id, username, email, password_hash, profile_picture, last_login FROM users";

        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password_hash");
                String profilePicture = rs.getString("profile_picture");
                Timestamp lastLogin = rs.getTimestamp("last_login");

                // Create a User object and add it to the list
                users.add(new User(userId, username, email, passwordHash, profilePicture, lastLogin));
            }
        }
        return users;
    }

}
