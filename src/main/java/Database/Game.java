package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Game extends DefaultData {

    private final String getUsername = Account.getUsername();

    public Game() throws SQLException {
    }

    /*
     ********************************************************************************************************************
     *Learning
     */
    /*
     ********************************************************************************************************************
     * Flashcard
     * 12/11
     */
    public ArrayList<String> getFlashcardFront() throws SQLException {
        String querySql = "SELECT word FROM av where id in (select id_av from practice) ";
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

    public ArrayList<String> getFlashcardBack(String front) throws SQLException {
        String sql = "SELECT description, pronounce FROM av where word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, front);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> back = new ArrayList<>();
                while (rs.next()) {
                    back.add(rs.getString("pronounce"));
                    back.add(rs.getString("description"));
                }
                return back;
            }
        }
    }

    /*
     ********************************************************************************************************************
     * Challenging
     * 11/11
     */
    public ArrayList<String> getQuestion() throws SQLException {
        String querySql = "SELECT question FROM practice";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("question"));
            }
            return list;
        }
    }

    public ArrayList<String> getChoice(String question) throws SQLException {
        String sql = "SELECT caseA, caseB, caseC FROM practice WHERE question = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> choiceList = new ArrayList<>();
                while (rs.next()) {
                    choiceList.add(rs.getString("caseA"));
                    choiceList.add(rs.getString("caseB"));
                    choiceList.add(rs.getString("caseC"));
                }
                return choiceList;
            }
        }
    }

    public String getAnswer(String question) throws SQLException {
        String sql = "SELECT word FROM av where id = (select id_av from practice WHERE question = ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("word");
                }
            }
        }
        return null;
    }

    public int getId(String question) throws SQLException {
        String sql = "SELECT id FROM practice WHERE question = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return 0;
    }

    //    public void setActiveAccount(String username, String password) {
//        String sql = "UPDATE account SET status = 1 WHERE username = ? AND password = ?";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void resetActiveAccount() {
//        String sql = "UPDATE account SET status = 0 WHERE status = 1";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void addMultipleChoicePoint(int point) throws SQLException {
//        String sql = "UPDATE account SET multipleChoicePoint = ? WHERE status = 1";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, point);
//            ps.executeUpdate();
//        }
//    }
//
//    public void addMatchingTime(int time) {
//        String sql = "UPDATE account SET matchingTime = ? WHERE status = 1";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, time);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /*
     ********************************************************************************************************************
     * Matching
     */
    public ArrayList<String> getMatchingWord() throws SQLException {
        String querySql = "SELECT word FROM av WHERE id BETWEEN 1 AND 58169 ORDER BY RANDOM() LIMIT 10";
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

    public String getMatchingMean(String word) throws SQLException {
        String sql = "SELECT description FROM av where word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("description");
                }
            }
        }
        return null;
    }

    /*
     ********************************************************************************************************************
     * Score
     */
    public int getMultipleChoicePoint() throws SQLException {
        String sql = "SELECT multipleChoicePoint from account where active = 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("multipleChoicePoint");
                }
            }
        }
        return 0;
    }

    public int getMatchingTime() throws SQLException {
        String sql = "SELECT matchingTime FROM account WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, getUsername);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("matchingTime");
                }
            }
        }
        return 0;
    }

    public void addMultipleChoicePoint(int point) throws SQLException {
        String sql = "UPDATE account SET multipleChoicePoint = ? WHERE active = 1 AND ? > multipleChoicePoint";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, point);
            ps.setInt(2, point);
            ps.executeUpdate();
        }
    }

    public void addMatchingTime(int time) {
        String sql = "UPDATE account SET matchingTime = ? WHERE active = 1 AND ? < matchingTime";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, time);
            ps.setInt(2, time);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}