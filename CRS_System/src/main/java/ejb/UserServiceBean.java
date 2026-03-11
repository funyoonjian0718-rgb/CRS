package ejb;

import jakarta.ejb.Stateless;
import dao.UserDAO;
import model.User;

@Stateless
public class UserServiceBean {

    public User login(String username, String password){

        UserDAO dao = new UserDAO();

        return dao.checkLogin(username, password);

    }
}