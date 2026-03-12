package controller;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import ejb.UserServiceBean;
import model.User;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    @EJB
    private UserServiceBean userService;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String status = request.getParameter("status");

        User user = new User();

        user.setUserId(id);
        user.setUsername(username);
        user.setRole(role);
        user.setEmail(email);
        user.setStatus(status);

        userService.updateUser(user);

        response.sendRedirect("viewUsers");
    }
}