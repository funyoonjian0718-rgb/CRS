package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewUsers")
public class ViewUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        UserDAO dao = new UserDAO();

        List<User> users = dao.getAllUsers();

        request.setAttribute("users", users);

        RequestDispatcher rd = request.getRequestDispatcher("manage_users.jsp");
        rd.forward(request, response);
    }
}