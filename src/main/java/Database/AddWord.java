package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddWord extends DefaultData {
    public AddWord() throws SQLException {
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
            ps.setString(2, pos + "; " + meaning);
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
}