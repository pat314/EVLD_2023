package Database;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account extends DefaultData {
    public Account() throws SQLException {
    }

    public static String getUsername() throws SQLException {
        String sql = "SELECT username FROM account WHERE active = 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return ps.executeQuery().getString("username");
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
}