package controller;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import ejb.UserServiceBean;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserServiceBean userService;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean valid = userService.login(username, password);

        if(valid){
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }

    }
}