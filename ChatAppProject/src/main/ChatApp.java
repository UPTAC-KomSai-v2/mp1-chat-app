package main;

import main.client.ChatClient;
import main.server.ChatServer;

public class ChatApp {
    public static void main(String[] args) {
        // Launch the client GUI or server setup based on command-line args
        if (args.length > 0 && args[0].equals("server")) {
            new ChatServer().start();
        } else {
            ChatClient.launch(ChatClient.class, args);
        }
    }
}
