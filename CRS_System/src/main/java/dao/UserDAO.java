package dao;

import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // LOGIN
    public User checkLogin(String username, String password) {

        User user = null;

        try {

            String sql = "SELECT * FROM users WHERE username=? AND password=? AND status='active'";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));

            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    // GET ALL USERS (DONE)
    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();

        try{

            String sql = "SELECT * FROM users";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));

                users.add(user);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return users;
    }

    // ADD USER (DONE)
    public void addUser(User user){

        try{

            String sql = "INSERT INTO users(username,password,role,email,status) VALUES (?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getStatus());

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // UPDATE USER (SAVES THE DATE INTO THE THE DATABASE AFTER THE CODE BELOW UPDATE IT IN EDIT_USER.JSP)
    public void updateUser(User user){

        try{

            String sql = "UPDATE users SET username=?, role=?, email=?, status=? WHERE user_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getStatus());
            ps.setInt(5, user.getUserId());

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    // ACTUALLY UPDATES THE USER AFTER PRESSING THE UPDATE BUTTON
    public User getUserById(int id){

        User user = null;

        try{

            String sql = "SELECT * FROM users WHERE user_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }
    

    // DEACTIVATE USER (DONE) 
    public void deactivateUser(int id){

        try{

            String sql = "UPDATE users SET status='inactive' WHERE user_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}