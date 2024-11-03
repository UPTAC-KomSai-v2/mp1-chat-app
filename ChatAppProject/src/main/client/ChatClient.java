package main.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChatClient extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize GUI components (e.g., LoginScreen)
        new LoginScreen().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
