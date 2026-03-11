package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import util.DatabaseConnection;

public class UserDAO {

    public User checkLogin(String username, String password) {

        User user = null;

        try {

            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username=? AND password=? AND status='active'";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return user;
    }
}