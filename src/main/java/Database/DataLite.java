package Database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DataLite {
    private static final String DB_URL_Lite = "jdbc:sqlite:src/main/java/Database/dict_t.db";
    private final HikariDataSource dataSource;

    public DataLite() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL_Lite);
        config.setMaximumPoolSize(20);
        dataSource = new HikariDataSource(config);
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

    /*
     ********************************************************************************************************************
     *Add Word, DeleteWord, Update Word
     */
    public boolean isExist(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addWord(String word, String pos, String breIpa, String[] meaning) throws SQLException {
        String meaningLine = "";
        String meaningSummary = "";
        for (String x : meaning) {
            meaningLine += "<li style=\"font-family: Segoe UI; font-size: 18px;\">" + x
                    + "</li>";
            meaningSummary += x + ", ";
        }


        String html = "<h1 style= \"color:#951D05; font-family: Segoe UI\">" + word
                + "</h1><h3 style=\"font-family: Segoe UI Light; color : #12225B\"><i>" + breIpa
                + "</i></h3><h2 style=\"font-family: Segoe UI;font-size: 20px;\">" + pos
                + "</h2><ul style=\"font-family: Segoe UI Bold; color :  #12225B ;\">" + meaningLine
                + "</ul>";
        String sql = "INSERT INTO av(word, description, html, pronounce) VALUES(?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ps.setString(2, pos + ". " + meaningSummary);
            ps.setString(3, html);
            ps.setString(4, breIpa);
            ps.executeUpdate();
        }
    }

    public void deleteWord(String s) throws SQLException {
        s = s.toLowerCase();
        String sql = "DELETE FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s);
            ps.executeUpdate();
        }
    }

    public void updateWord(String word, String detail, String old_word) throws SQLException { //, String pos, String breIpa, String nameIpa, String meaning, String old_word
        //String html = convertToHTML(word + "<br>" +breIpa, pos, meaning);
        word = word.toLowerCase();
        String sql = "UPDATE av SET word = ?, html = ? WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, detail);
//            ps.setString(3, nameIpa);
//            ps.setString(4, pos + "; " +meaning);
            ps.setString(3, old_word);
            ps.executeUpdate();
        }
    }

    /*
     ********************************************************************************************************************
     *Login
     */
    public boolean checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void setActiveAccount(String username, String password) throws SQLException {
        String sql = "UPDATE account SET active = 1 WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        }
    }

    public void resetActiveAccount() {
        String sql = "UPDATE account SET active = 0 WHERE active = 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() throws SQLException {
        String sql = "SELECT username FROM account WHERE active = 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return ps.executeQuery().getString("username");
        }
    }

    /*
     ********************************************************************************************************************
     *SignUp
     */
    public boolean isExistAccount(String username) {
        String sql = "SELECT COUNT(*) FROM account WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(new PrintStream(System.out));
        }
        return false;
    }

    public void signUp(String username, String password, String email) throws SQLException {
        String sql = "INSERT INTO account(username, password, email) VALUES(?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.executeUpdate();
        }
    }

    /*
     ********************************************************************************************************************
     *Delete Account
     */
    public boolean checkAccount(String username, String password, String email) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ? AND email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void deleteAccount(String username, String password, String email) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ? AND email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) != 0) {
                    String sql1 = "DELETE FROM account WHERE username = ? AND password = ? AND email = ?";
                    try (PreparedStatement deleteStatement = connection.prepareStatement(sql1)) {
                        deleteStatement.setString(1, username);
                        deleteStatement.setString(2, password);
                        deleteStatement.setString(3, email);
                        deleteStatement.executeUpdate();
                    }
                }
            }
        }
    }

    /*
     ********************************************************************************************************************
     *Change Password
     */

    public boolean checkPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void changePassword(String username, String password, String new_password) throws SQLException {
        String sql = "UPDATE account SET password = ? WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, new_password);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();
        }
    }

    /*
     ********************************************************************************************************************
     *Favorite Word
     */
    public boolean isExistFavorite(String word) throws SQLException {
        String username = getUsername();
        String sql = "SELECT COUNT(*) FROM avfavorite WHERE word = ? AND username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addFavorite(String word) throws SQLException {
        String username = getUsername();
        String sql = "INSERT INTO avfavorite(word, html, description, pronounce, username) SELECT word, html, description, pronounce, ? FROM av WHERE word=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
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
        String username = getUsername();
        String sql = "DELETE FROM avfavorite WHERE word=? and username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }

    public ArrayList<String> getFavorite() throws SQLException {
        String username = getUsername();
        String querySql = "SELECT word FROM avfavorite where username = ? group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    public ArrayList<String> getFavoritePOS() throws SQLException {
        String username = getUsername();
        String querySql = "SELECT pronounce FROM avfavorite where username = ? group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("pronounce"));
            }
            return list;
        }
    }

    public ArrayList<String> getFavoriteDetail() throws SQLException {
        String username = getUsername();
        String querySql = "SELECT description FROM avfavorite where username = ? group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("description"));
            }
            return list;
        }
    }

    public ArrayList<String> suggestWordsFa(String input) throws SQLException {
        String username = getUsername();
        input = input.toLowerCase();
        String sql = "SELECT word FROM avfavorite WHERE word LIKE ? AND username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            ps.setString(2, username);
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
        String username = getUsername();
        input = input.toLowerCase();
        String sql = "SELECT pronounce FROM avfavorite WHERE word LIKE ? AND username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            ps.setString(2, username);
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
        String username = getUsername();
        input = input.toLowerCase();
        String sql = "SELECT description FROM avfavorite WHERE word LIKE ? AND username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            ps.setString(2, username);
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
        String username = getUsername();
        String sql = "DELETE FROM avfavorite WHERE username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
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
            ps.setString(2, getUsername());
            ps.executeUpdate();
        }
    }

    public ArrayList<String> getHistory() throws SQLException {
        String querySql = "SELECT word FROM avhistory where username = ? order by id desc limit 20";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql)) {
            ps.setString(1, getUsername());
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
            ps.setString(1, getUsername());
            ps.executeUpdate();
        }
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
            ps.setString(1, getUsername());
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