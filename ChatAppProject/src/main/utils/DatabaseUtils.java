package main.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_database_username";
    private static final String DB_PASSWORD = "your_database_password";

    public static Connection getConnection() throws SQLException {
        // Create and return a new connection to the database
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
