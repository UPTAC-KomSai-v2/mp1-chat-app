package main.models;

import java.sql.*;
import java.time.LocalDateTime;

import main.utils.DatabaseUtils;

public class Channel {
    private int channelId;
    private String channelName;
    private boolean isPrivate;
    private int createdBy;
    private Timestamp createdAt;

    // Constructor
    public Channel(int channelId, String channelName, boolean isPrivate, int createdBy, Timestamp createdAt) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.isPrivate = isPrivate;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Channel(){

    }

    // Getters and Setters
    public int getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // CRUD operations using JDBC
    public static Channel getChannelById(int id) throws SQLException {
        String query = "SELECT * FROM Channels WHERE channel_id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Channel(
                        rs.getInt("channel_id"),
                        rs.getString("channel_name"),
                        rs.getBoolean("is_private"),
                        rs.getInt("created_by"),
                        rs.getTimestamp("created_at")
                );
            }
        }
        return null;
    }

    public void save() throws SQLException {
        String query = "INSERT INTO Channels (channel_name, is_private, created_by, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.channelName);
            stmt.setBoolean(2, this.isPrivate);
            stmt.setInt(3, this.createdBy);
            stmt.setTimestamp(4, this.createdAt);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.channelId = generatedKeys.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        String query = "UPDATE Channels SET channel_name = ?, is_private = ?, created_by = ?, created_at = ? WHERE channel_id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.channelName);
            stmt.setBoolean(2, this.isPrivate);
            stmt.setInt(3, this.createdBy);
            stmt.setTimestamp(4, this.createdAt);
            stmt.setInt(5, this.channelId);
            stmt.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        String query = "DELETE FROM Channels WHERE channel_id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.channelId);
            stmt.executeUpdate();
        }
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = Timestamp.valueOf(createdAt);
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
