package main.server;

import main.models.User;
import main.utils.TokenUtils;
import main.server.ServerDatabase;
import java.io.PrintWriter;

public class AuthController {

    private ServerDatabase database = new ServerDatabase();

    public void handleLogin(String usernameOrEmail, String password, PrintWriter out) {
        User user = database.getUserByUsername(usernameOrEmail);

        if (user != null && EncryptionUtils.verifyPassword(password, user.getPasswordHash())) {
            String token = TokenUtils.generateToken(user);
            out.println("SUCCESS " + token); // Send token on success
        } else {
            out.println("ERROR Invalid credentials"); // Send error on failure
        }
    }
}