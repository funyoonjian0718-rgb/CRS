package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import dao.UserDAO;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String inputOtp = request.getParameter("otp");
        String newPassword = request.getParameter("password");

        HttpSession session = request.getSession();

        String sessionOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");

        if(sessionOtp != null && sessionOtp.equals(inputOtp)){

            UserDAO dao = new UserDAO();
            dao.updatePasswordByEmail(email, newPassword);

            response.sendRedirect("login.jsp");

        } else {
            response.getWriter().println("Invalid OTP");
        }
    }
}