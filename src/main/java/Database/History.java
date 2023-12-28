package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class History extends DefaultData {
    private final String getUsername = Account.getUsername();

    public History() throws SQLException {
    }

    /*
     ********************************************************************************************************************
     *History Word
     */
    public void addHistory(String word) throws SQLException {
        String sql = "INSERT INTO avhistory(word, username) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, getUsername);
            ps.executeUpdate();
        }
    }

    public ArrayList<String> getHistory() throws SQLException {
        String querySql = "SELECT word FROM avhistory where username = ? order by id desc limit 20";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, getUsername);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    public void deleteHistory() throws SQLException {
        String deleteSql = "DELETE FROM avhistory where username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {
            ps.setString(1, getUsername);
            ps.executeUpdate();
        }
    }
}