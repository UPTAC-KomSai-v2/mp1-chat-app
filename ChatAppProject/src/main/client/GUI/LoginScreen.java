package main.client.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.regex.Pattern;

public class LoginScreen {

    public void start(Stage primaryStage) {
        // Create tabs for login and signup
        TabPane tabPane = new TabPane();

        // Login Tab
        Tab loginTab = new Tab("Login");
        loginTab.setClosable(false);
        GridPane loginGrid = createLoginGridPane();
        loginTab.setContent(loginGrid);

        // Signup Tab
        Tab signupTab = new Tab("Signup");
        signupTab.setClosable(false);
        GridPane signupGrid = createSignupGridPane();
        signupTab.setContent(signupGrid);

        tabPane.getTabs().addAll(loginTab, signupTab);

        // Scene setup
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ChatApp - Login & Signup");
        primaryStage.show();
    }

    private GridPane createLoginGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // Username field
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        // Password field
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        // Login button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        gridPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        return gridPane;
    }

    private GridPane createSignupGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // Username field
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        // Email field
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);
        TextField emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 1);

        // Password field
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 2);

        // Signup button
        Button signupButton = new Button("Signup");
        GridPane.setConstraints(signupButton, 1, 3);
        signupButton.setOnAction(e -> handleSignup(usernameField.getText(), emailField.getText(), passwordField.getText()));

        gridPane.getChildren().addAll(usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField, signupButton);

        return gridPane;
    }

    private void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username and password cannot be empty!");
            return;
        }

        if (authenticateUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
            // Proceed to main chat window
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid credentials. Please try again.");
        }
    }

    private void handleSignup(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email format!");
            return;
        }

        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters long, include a number and a special character!");
            return;
        }

        if (registerUser(username, email, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Signup successful! You can now log in.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "User already exists or error during registration.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private boolean authenticateUser(String username, String password) {
        return "testuser".equals(username) && "password123!".equals(password);
    }

    private boolean registerUser(String username, String email, String password) {
        return true; // Simulate successful registration
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
