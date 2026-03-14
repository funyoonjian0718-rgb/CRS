package controller;

import dao.EligibilityDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/registerStudent")
public class RegisterStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("studentId");

        EligibilityDAO dao = new EligibilityDAO();

        boolean result = dao.registerStudent(studentId);

        if(result){
            response.sendRedirect("checkEligibility");
        }else{
            response.getWriter().println("Registration Failed");
        }
    }
}