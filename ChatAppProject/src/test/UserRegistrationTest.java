package test;

import main.server.UserRegistration;
import java.sql.SQLException;

public class UserRegistrationTest {

    public static void main(String[] args) throws SQLException {
        UserRegistration userRegistration = new UserRegistration();

        // Test case 1: Valid input
        String response = userRegistration.registerUser(5, "cheol_kim", "cheolkim@example.com", "Password123");
        System.out.println("Test case 1: " + response);

        // Test case 2: Invalid email format
        response = userRegistration.registerUser(6, "miae_hwang", "miaehwang.com", "Password123");
        System.out.println("Test case 2: " + response);

        // Test case 3: Email already exists
        response = userRegistration.registerUser(7,"seo_jisu", "cheolkim@example.com", "Password123");
        System.out.println("Test case 3: " + response);

        // Test case 4: Invalid password
        response = userRegistration.registerUser(8,"jinseop", "jinseop1@gmail.com", "ok");
        System.out.println("Test case 4: " + response);

    }

}
