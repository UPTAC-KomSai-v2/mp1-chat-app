package main.client.GUI;

import javax.swing.JFrame;
import javafx.stage.Stage;

public class LoginScreen {
    public void start(Stage primaryStage) {
        // Create a new frame
        JFrame frame = new JFrame("500x500 Frame");

        // Set the size of the frame to 500x500 pixels
        frame.setSize(500, 500);

        // Set the default close operation to exit the application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        frame.setVisible(true);
    }
}
