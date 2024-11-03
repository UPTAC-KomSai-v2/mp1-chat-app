# ChatApp Project

## Overview
ChatApp is a Java-based desktop application that provides a real-time chat system with features similar to popular messaging platforms like Slack or Discord. The application includes both server and client components, ensuring secure communication, user authentication, and message storage using a multi-threaded client-server architecture.

## Project Structure
The project is organized into various packages to ensure modularity and easy maintainability. Below is the directory structure:

```plaintext
ChatApp/
├── src/
│   ├── main/
│   │   ├── ChatApp.java                # Main entry point for server/client
│   │   ├── server/
│   │   │   ├── ChatServer.java         # Main server class for managing connections
│   │   │   ├── ClientHandler.java      # Handles individual client connections
│   │   │   ├── EncryptionUtils.java    # Utility class for AES encryption/decryption
│   │   │   └── ServerDatabase.java     # Handles server-side database interactions
│   │   ├── client/
│   │   │   ├── ChatClient.java         # Main client class for initiating connections
│   │   │   ├── Notifications.java      # Handles user notifications
│   │   │   ├── FileHandler.java        # Manages file upload and download
│   │   │   └── GUI/
│   │   │       ├── LoginScreen.java    # Login and signup GUI screen
│   │   │       ├── ChatWindow.java     # Main chat window interface
│   │   │       ├── ChannelList.java    # Displays and manages channels
│   │   │       └── SettingsPanel.java  # User settings and profile management
│   │   ├── models/
│   │   │   ├── Message.java            # Data model for messages
│   │   │   ├── User.java               # Data model for user information
│   │   │   ├── Channel.java            # Data model for channels
│   │   │   └── Notification.java       # Data model for notifications
│   │   └── utils/
│   │       ├── JsonUtils.java          # Utility class for handling JSON serialization
│   │       └── DatabaseUtils.java      # Utility class for database connections
│   └── test/
│       ├── ChatServerTest.java         # Unit tests for server-side logic
│       ├── ChatClientTest.java         # Unit tests for client-side logic
│       └── EncryptionUtilsTest.java    # Unit tests for encryption utility
├── lib/
│   ├── mysql-connector-java.jar        # MySQL JDBC driver for database interactions
│   └── other dependencies...           # Additional dependencies if needed
├── resources/
│   ├── schema.sql                      # Database schema for initial setup
│   ├── application.properties          # Configuration file for database and app settings
│   └── images/
│       └── icon.png                    # Application icons or other assets
├── README.md                           # This readme file
└── ChatApp.jar                         # Packaged JAR for deployment
```
## Requirements
- Java 8 or higher 
- JavaFX SDK for building the GUI
- MySQL or SQLite for database management 
- IntelliJ IDEA (recommended) or VSCode with appropriate plugins

## Setup Instructions
1. Clone the repository:
```plaintext
git clone https://github.com/yourusername/ChatApp.git
cd ChatApp
```
2. Install Java and JavaFX: Ensure Java 8 or higher is installed. Download Java and JavaFX SDK. 
3. Configure JavaFX:
   - Add JavaFX libraries to your project:
     - In IntelliJ IDEA: Go to File > Project Structure > Libraries, and add the JavaFX SDK lib directory. 
   - Add the following VM options in your run configurations:
   ```plaintext 
   --module-path <path-to-javafx-sdk-lib> --add-modules javafx.controls,javafx.fxml
4. Set Up the Database:
   - Install and set up MySQL or SQLite. 
   - Run the schema.sql file in the resources folder to create the necessary tables:
   ```plaintext
   CREATE TABLE Users (
       user_id INT PRIMARY KEY AUTO_INCREMENT,
       username VARCHAR(50) NOT NULL UNIQUE,
       email VARCHAR(100) NOT NULL UNIQUE,
       password_hash VARCHAR(255) NOT NULL,
       role ENUM('user', 'moderator', 'admin') DEFAULT 'user',
       last_login TIMESTAMP
   );
   -- Add other tables as per the schema
   ```
5. Run the Server:
   - Open IntelliJ IDEA and create a Run Configuration named RunServer:
       - Main class: main.ChatApp
       - Program arguments: server
       - Add VM options for JavaFX.
   - Click Run to start the server. 
6. Run the Client:
    - Create another Run Configuration named RunClient:
        - Main class: main.ChatApp
        - Program arguments: Leave empty (optional).
    - Run this configuration to start the client interface.

## Future Development
- Implement additional security features and error handling.
- Enhance the GUI for better user experience.
- Add more tests to improve code coverage and ensure stability.

## Contribution Guidelines
1. Fork the repository.
2. Create a branch for your feature:
```plaintext
git checkout -b feature/new-feature
```
3. Commit your changes:
```plaintext
git commit -m "Add new feature"
```
4. Push to the branch:

```plaintext
git push origin feature/new-feature
```
5. Create a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.