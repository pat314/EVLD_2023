package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Home extends DefaultData {
    public Home() throws SQLException {
    }

    /*
    ********************************************************************************************************************
     author: anh tuan
     * Dictionary
     */
    public String searchWord(String s) throws SQLException {
        String sql = "SELECT * FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("html");
                }
            }
        }
        return null;
    }

    public ArrayList<String> suggestWords(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT word FROM av WHERE word LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("word"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> getListWord() throws SQLException {
        String querySql = "SELECT word FROM av limit 58169 ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    /*
********************************************************************************************************************
 author: anh tu
 * Word of the day
 */
    public String searchWordbyID(int num) throws SQLException {
        String sql = "SELECT word FROM av WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(num));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("word");
                }
            }
        }
        return null;
    }

    public String searchPOSbyID(int num) throws SQLException {
        String sql = "SELECT html FROM av WHERE id = ? AND fav != ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(num));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String s = rs.getString("html");
                    if (s.contains("danh từ")) return "noun";
                    else if (s.contains("động từ")) return "verb";
                    else if (s.contains("tính từ")) return "adjective";
                    else if (s.contains("giới từ")) return "preposition";
                    else if (s.contains("trạng từ") || s.contains("phó từ")) return "adverb";
                    else if (s.contains("đại từ")) return "pronoun";
                    else return "";
                }
            }
        }
        return null;
    }
}