package main.server;

import main.models.User;
import main.utils.DatabaseUtils;
import java.sql.*;

public class ServerDatabase {

    public User getUserByUsername(String username) {
        try (Connection connection = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
