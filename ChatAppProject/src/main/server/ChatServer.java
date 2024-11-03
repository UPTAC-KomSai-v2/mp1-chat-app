package main.server;

public class ChatServer {
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(8080); // Port number
            System.out.println("Server started on port 8080");

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
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
