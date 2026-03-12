package controller;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import ejb.UserServiceBean;
import model.User;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {

    @EJB
    private UserServiceBean userService;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        User user = userService.getUserById(id);

        request.setAttribute("user", user);

        RequestDispatcher rd =
                request.getRequestDispatcher("edit_user.jsp");

        rd.forward(request, response);
    }
}