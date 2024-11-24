CREATE DATABASE IF NOT EXISTS chatapp_db;
use chatapp_db;

CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(255),
    role ENUM('user', 'moderator', 'admin') NOT NULL,
    last_login DATETIME
);

CREATE TABLE Channels (
    channel_id INT PRIMARY KEY,
    channel_name VARCHAR(255) NOT NULL,
    is_private BOOLEAN DEFAULT FALSE,
    created_by INT,
    created_at DATETIME,
    FOREIGN KEY (created_by) REFERENCES Users(user_id) ON DELETE SET NULL
);

CREATE TABLE Channel_Managers (
    manager_id INT PRIMARY KEY,
    channel_id INT,
    user_id INT,
    assigned_at DATETIME,
    role ENUM('admin', 'moderator') NOT NULL,
    FOREIGN KEY (channel_id) REFERENCES Channels(channel_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Channel_Members (
    channel_member_id INT PRIMARY KEY,
    channel_id INT,
    user_id INT,
    joined_at DATETIME,
    FOREIGN KEY (channel_id) REFERENCES Channels(channel_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Messages (
    message_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    channel_id INT,
    content TEXT,
    timestamp DATETIME NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    is_flagged BOOLEAN DEFAULT FALSE,
    deleted_by_admin BOOLEAN DEFAULT FALSE,
    receiver_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (channel_id) REFERENCES Channels(channel_id) ON DELETE SET NULL,
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id) ON DELETE SET NULL
);

CREATE TABLE Files (
    file_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    message_id INT,
    file_path VARCHAR(255) NOT NULL,
    file_name VARCHAR(255),
    file_type VARCHAR(50),
    uploaded_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (message_id) REFERENCES Messages(message_id) ON DELETE CASCADE
);

CREATE TABLE Notifications (
    notification_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    message_id INT,
    channel_id INT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (message_id) REFERENCES Messages(message_id) ON DELETE CASCADE,
    FOREIGN KEY (channel_id) REFERENCES Channels(channel_id) ON DELETE CASCADE
);

ALTER TABLE Users
DROP COLUMN role;


#POPULATE TABLES

-- Insert sample data into Users table
INSERT INTO Users (user_id, username, email, password_hash, profile_picture, role, last_login)
VALUES
    (1, 'alice', 'alice@example.com', 'hashed_password_1', 'alice_pic.png', '2024-10-30 08:00:00'),
    (2, 'bob', 'bob@example.com', 'hashed_password_2', 'bob_pic.png', '2024-10-30 09:30:00'),
    (3, 'charlie', 'charlie@example.com', 'hashed_password_3', 'charlie_pic.png', '2024-10-31 11:45:00'),
    (4, 'dave', 'dave@example.com', 'hashed_password_4', NULL, '2024-10-31 12:00:00');

INSERT INTO Channels (channel_id, channel_name, is_private, created_by, created_at)
VALUES
    (1, 'General', FALSE, 1, NOW()),
    (2, 'Private Discussion', TRUE, 2, NOW()),
    (3, 'Announcements', FALSE, 1, NOW()),
    (4, 'Development', FALSE, 3, NOW()),
    (5, 'Off-Topic', FALSE, 2, NOW());

-- Populate Channel_Members table
INSERT INTO Channel_Members (channel_member_id, channel_id, user_id, joined_at)
VALUES
    (1, 1, 1, NOW()),
    (2, 2, 2, NOW()),
    (3, 3, 1, NOW()),
    (4, 4, 3, NOW()),
    (5, 5, 2, NOW()),
    (6, 1, 3, NOW()),
    (7, 4, 4, NOW()),
    (8, 5, 1, NOW());

-- Populate Channel_Managers table
INSERT INTO Channel_Managers (manager_id, channel_id, user_id, assigned_at, role)
VALUES
    (1, 1, 1, NOW(), 'admin'),
    (2, 2, 2, NOW(), 'moderator'),
    (3, 3, 1, NOW(), 'moderator'),
    (4, 4, 3, NOW(), 'admin');


