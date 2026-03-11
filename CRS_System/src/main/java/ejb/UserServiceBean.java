package ejb;

import jakarta.ejb.Stateless;

@Stateless
public class UserServiceBean {

    public boolean login(String username, String password){

        if(username.equals("admin") && password.equals("1234")){
            return true;
        }

        return false;
    }
}