package main.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DatabaseUtils {
    private static String url;
    private static String username;
    private static String password;

    // Load database properties when the class is first loaded
    static {
        try (InputStream input = DatabaseUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                //return;
            }

            // Load properties file
            properties.load(input);

            // Assign database configuration from properties
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");

            // Load the database driver
            String driver = properties.getProperty("db.driver");
            if (driver != null) {
                Class.forName(driver);
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get a connection to the database.
     * 
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
