package controller;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import ejb.UserServiceBean;

@WebServlet("/deactivateUser")
public class DeactivateUser extends HttpServlet {

    @EJB
    private UserServiceBean userService;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        userService.deactivateUser(id);

        response.sendRedirect("viewUsers");
    }
}