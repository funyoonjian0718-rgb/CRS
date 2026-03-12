package controller;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import ejb.UserServiceBean;
import model.User;

@WebServlet("/addUser")
public class AddUsersServlet extends HttpServlet {

    @EJB
    private UserServiceBean userService;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        user.setStatus("active");

        userService.addUser(user);

        response.sendRedirect("viewUsers");

    }
}