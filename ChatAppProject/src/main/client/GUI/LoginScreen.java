package main.client.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginScreen {

    private VBox loginBox;
    private VBox signupBox;
    private StackPane formContainer; // Container for toggling forms
    private Stage primaryStage;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create a header for the application
        Text header = new Text("Chat App");
        header.setFont(Font.font("Arial", 24));
        header.setStyle("-fx-font-weight: bold;");

        // Add the header to a VBox for alignment and reduced padding
        VBox headerBox = new VBox(header);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10, 0, 10, 0)); // Reduced padding to bring it closer to the form

        // Initialize login and signup forms
        loginBox = createLoginBox();
        signupBox = createSignupBox();

        // Create a StackPane to hold both forms
        formContainer = new StackPane(loginBox, signupBox);
        signupBox.setVisible(false); // Show only the login form initially

        // Create a main layout that includes the header and form container
        VBox mainLayout = new VBox(10, headerBox, formContainer);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 500); // Set smaller size for pop-up
        scene.getStylesheets().add("main/client/GUI/styles.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat App - Login & Signup");
        primaryStage.setResizable(false); // Make the window non-resizable
        primaryStage.centerOnScreen(); // Center the stage on the screen
        primaryStage.show();
    }

    private VBox createLoginBox() {
        GridPane gridPane = createFormGridPane();
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Sign in");
        loginButton.setId("login-button");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Add a toggle link to go to the signup form
        Text signupLink = new Text("Don't have an account? Sign up");
        signupLink.setStyle("-fx-fill: #4285f4; -fx-underline: true;");
        signupLink.setOnMouseClicked(e -> switchToSignup());

        VBox vbox = new VBox(10, gridPane, loginButton, signupLink);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        return vbox;
    }

    private VBox createSignupBox() {
        GridPane gridPane = createFormGridPane();
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button signupButton = new Button("Sign up");
        signupButton.setId("signup-button");
        signupButton.setOnAction(e -> handleSignup(usernameField.getText(), passwordField.getText()));

        // Add a toggle link to go to the login form
        Text loginLink = new Text("Already have an account? Sign in");
        loginLink.setStyle("-fx-fill: #4285f4; -fx-underline: true;");
        loginLink.setOnMouseClicked(e -> switchToLogin());

        VBox vbox = new VBox(10, gridPane, signupButton, loginLink);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        return vbox;
    }

    private GridPane createFormGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        return gridPane;
    }

    private void switchToLogin() {
        loginBox.setVisible(true);
        signupBox.setVisible(false);
    }

    private void switchToSignup() {
        loginBox.setVisible(false);
        signupBox.setVisible(true);
    }

    private void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username and password cannot be empty!");
            return;
        }

        // Simulate login success or failure
        if ("testuser".equals(username) && "Password1!".equals(password)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid credentials. Please try again.");
        }
    }

    private void handleSignup(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters long!");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Success", "Signup successful! You can now log in.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
