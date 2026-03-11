package controller;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import ejb.UserServiceBean;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserServiceBean userService;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.login(username, password);

        if(user != null){

            HttpSession session = request.getSession();

            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            if(user.getRole().equals("course_admin")){
                response.sendRedirect("admin_dashboard.jsp");
            }else{
                response.sendRedirect("officer_dashboard.jsp");
            }

        } else {

            response.sendRedirect("login.jsp?error=1");

        }
    }
}