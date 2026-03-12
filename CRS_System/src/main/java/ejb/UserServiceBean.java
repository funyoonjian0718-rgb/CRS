package ejb;

import jakarta.ejb.Stateless;

import java.util.List;

import dao.UserDAO;
import model.User;

@Stateless
public class UserServiceBean {

    public User login(String username, String password){

        UserDAO dao = new UserDAO();
        return dao.checkLogin(username, password);

    }

    public void addUser(User user){

        UserDAO dao = new UserDAO();
        dao.addUser(user);

    }

    public List<User> getAllUsers(){

        UserDAO dao = new UserDAO();
        return dao.getAllUsers();

    }

    public void updateUser(User user){

        UserDAO dao = new UserDAO();
        dao.updateUser(user);

    }

    public void deactivateUser(int id){

        UserDAO dao = new UserDAO();
        dao.deactivateUser(id);

    }
}