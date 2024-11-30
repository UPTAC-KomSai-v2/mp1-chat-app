package test;

import main.server.UserLoginController;

import java.util.HashMap;
import java.util.Map;

public class UserLoginControllerTest {

    public static void main(String[] args) {
        UserLoginController userLoginController = new UserLoginController();

        // Test case 1: Valid login using email
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("emailOrUsername", "cheolkim@example.com");
        loginRequest.put("password", "Password123");
        System.out.println("Test case 1: " + userLoginController.login(loginRequest).getBody());

        // Test case 2: Valid login using username
        loginRequest = new HashMap<>();
        loginRequest.put("emailOrUsername", "cheol_kim");
        loginRequest.put("password", "Password123");
        System.out.println("Test case 2: " + userLoginController.login(loginRequest).getBody());

        // Test case 3: Invalid password
        loginRequest = new HashMap<>();
        loginRequest.put("emailOrUsername", "alice@example.com");
        loginRequest.put("password", "wrong_password");
        System.out.println("Test case 3: " + userLoginController.login(loginRequest).getBody());

        // Test case 4: User not found
        loginRequest = new HashMap<>();
        loginRequest.put("emailOrUsername", "unknown@example.com");
        loginRequest.put("password", "password123");
        System.out.println("Test case 4: " + userLoginController.login(loginRequest).getBody());
    }
}
