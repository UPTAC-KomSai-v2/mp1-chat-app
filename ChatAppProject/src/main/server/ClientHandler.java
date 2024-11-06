// src/main/server/ClientHandler.java

package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private AuthController authController;

    public ClientHandler(Socket socket, AuthController authController) {
        this.clientSocket = socket;
        this.authController = authController; // Pass AuthController instance to handle login
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);

                // Check if the message is a login request
                if (message.startsWith("LOGIN")) {
                    handleLogin(message);
                } else {
                    // Handle other commands here
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleLogin(String message) {
        // Expecting message format: LOGIN <usernameOrEmail> <password>
        String[] parts = message.split(" ");
        if (parts.length == 3) {
            String usernameOrEmail = parts[1];
            String password = parts[2];

            // Use AuthController to handle login and send response
            authController.handleLogin(usernameOrEmail, password, out);
        } else {
            out.println("ERROR Invalid login format. Use: LOGIN <usernameOrEmail> <password>");
        }
    }
}
