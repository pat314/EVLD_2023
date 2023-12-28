package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Favorite extends DefaultData {
    public String getUsername = Account.getUsername();

    public Favorite() throws SQLException {
    }

    /*
     ********************************************************************************************************************
     *Favorite Word
     */
    public boolean isExistFavorite(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM avfavorite WHERE word = ? AND username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ps.setString(2, getUsername);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addFavorite(String word) throws SQLException {
        String sql = "INSERT INTO avfavorite(word, html, description, pronounce, username) SELECT word, html, description, pronounce, ? FROM av WHERE word=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, getUsername);
            ps.setString(2, word);
            ps.executeUpdate();
        }
    }

    /**
     * Delete a favourite word from favourite table.
     *
     * @param word the word to be excluded from the table
     * @throws SQLException SQL Exception
     */
    public void deleteFavorite(String word) throws SQLException {
        String sql = "DELETE FROM avfavorite WHERE word=? and username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, getUsername);
            ps.executeUpdate();
        }
    }

    public ArrayList<String> getFavorite() throws SQLException {
        String querySql = "SELECT word FROM avfavorite where username = ? group by word";
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

    public ArrayList<String> getFavoritePOS() throws SQLException {
        String querySql = "SELECT pronounce FROM avfavorite where username = ? group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, getUsername);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("pronounce"));
            }
            return list;
        }
    }

    public ArrayList<String> getFavoriteDetail() throws SQLException {
        String querySql = "SELECT description FROM avfavorite where username = ? group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, getUsername);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("description"));
            }
            return list;
        }
    }

    public ArrayList<String> suggestWordsFa(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT word FROM avfavorite WHERE word LIKE ? AND username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            ps.setString(2, getUsername);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("word"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> suggestPronounceFa(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT pronounce FROM avfavorite WHERE word LIKE ? AND username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            ps.setString(2, getUsername);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("pronounce"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> suggestDetailFa(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT description FROM avfavorite WHERE word LIKE ? AND username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            ps.setString(2, getUsername);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("description"));
                }
                return wordList;
            }
        }
    }

    public void deleteFavorite() throws SQLException {
        String sql = "DELETE FROM avfavorite WHERE username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, getUsername);
            ps.executeUpdate();
        }
    }

    public String getUsername() {
        return getUsername;
    }
}