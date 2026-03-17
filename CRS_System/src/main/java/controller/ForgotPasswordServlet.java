package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Random;

import dao.UserDAO;
import model.User;
import util.EmailService;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();

        User user = dao.getUserByEmail(email);

        if(user != null){

            String otp = String.valueOf(new Random().nextInt(999999));

            HttpSession session = request.getSession();
            session.setAttribute("otp", otp);
            session.setAttribute("email", email);

            EmailService.sendEmail(email,
                    "Password Reset OTP",
                    "Your OTP is: " + otp);

            response.sendRedirect("reset_password.jsp");

        } else {
            response.getWriter().println("Email not found");
        }
    }
}