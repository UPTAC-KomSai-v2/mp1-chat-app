package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private ServerSocket serverSocket;
    private AuthController authController;

    public ChatServer() {
        authController = new AuthController(); // Initialize AuthController
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8080); // Port number
            System.out.println("Server started on port 8080");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, authController).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}