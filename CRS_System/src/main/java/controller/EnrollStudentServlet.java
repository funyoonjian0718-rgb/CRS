package controller;

import dao.RecoveryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/enrollStudent")
public class EnrollStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("studentId");

        RecoveryDAO dao = new RecoveryDAO();

        boolean result = dao.enrollStudent(studentId);

        if(result){
            response.sendRedirect("checkEligibility");
        }else{
            response.getWriter().println("Recovery enrollment failed");
        }
    }
}