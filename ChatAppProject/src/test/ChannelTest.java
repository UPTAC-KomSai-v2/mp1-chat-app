package test;

import main.models.Channel;
import main.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChannelTest {

    public static void main(String[] args) {
        try {
            Channel firstChannel = getFirstChannel();
            if (firstChannel != null) {
                System.out.println("Channel ID: " + firstChannel.getChannelId());
                System.out.println("Channel Name: " + firstChannel.getChannelName());
                System.out.println("Is Private: " + firstChannel.isPrivate());
                System.out.println("Created At: " + firstChannel.getCreatedAt());
                System.out.println("Created By: " + firstChannel.getCreatedBy());
            } else {
                System.out.println("No channels found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Channel getFirstChannel() throws SQLException {
        String query = "SELECT * FROM Channels LIMIT 1";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                Channel channel = new Channel();
                channel.setChannelId(rs.getInt("channel_id"));
                channel.setChannelName(rs.getString("channel_name"));
                channel.setPrivate(rs.getBoolean("is_private"));
                channel.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                channel.setCreatedBy(rs.getInt("created_by"));
                return channel;
            }

        }
        return null;
    }
}
