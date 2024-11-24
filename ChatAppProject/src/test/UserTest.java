package test;

import main.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserTest {

    public static void main(String[] args) {
        try {
            testGetChannelsWithRoles();
            //testGetAllUser();
            //testUserSave();
            //testUserUpdate();
            //testGetUserById();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Test getChannelsWithRoles function
    public static void testGetChannelsWithRoles() throws SQLException {
        System.out.println("Testing getChannelsWithRoles...");

        User testUser = new User();
        testUser.setUserId(3); // Example user ID

        List<String[]> channelsWithRoles = testUser.getChannelsWithRoles();

        if (!channelsWithRoles.isEmpty()) {
            for (String[] channel : channelsWithRoles) {
                System.out.println("Channel ID: " + channel[0]);
                System.out.println("Channel Name: " + channel[1]);
                System.out.println("Private: " + channel[2]);
                System.out.println("User ID: " + channel[3]);
                System.out.println("Role: " + channel[4]);
                System.out.println("--------------------");
            }
        } else {
            System.out.println("No channels found for the user.");
        }
    }

    public static void testGetAllUser() throws SQLException{
        User testUser = new User();
        // Call the getAllUsers method
        List<User> users = testUser.getAllUsers();

        // Check if the list is empty
        if (users.isEmpty()) {
            System.out.println("No users found in the database.");
        } else {
            // Print each user's details
            System.out.println("All Users:");
            for (User user : users) {
                System.out.println("------------------");
                System.out.println("ID: " + user.getUserId());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Profile Picture: " + user.getProfilePicture());
                System.out.println("Last Login: " + user.getLastLogin());
            }
        }

    }

    // Test save function (assuming a `save` method exists in the User model)
    public static void testUserSave() throws SQLException {

    }

    // Test update function (assuming an `update` method exists in the User model)
    public static void testUserUpdate() throws SQLException {

    }

    // Test getUserById function
    public static void testGetUserById() throws SQLException {

    }
}
