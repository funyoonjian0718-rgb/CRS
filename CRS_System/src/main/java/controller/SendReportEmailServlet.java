package controller;

import dao.PerformanceReportDAO;
import dao.UserDAO;
import model.PerformanceReport;
import util.DatabaseConnection;
import util.EmailService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/sendReportEmail")
public class SendReportEmailServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("studentId");

        String studentName = "";
        String program = "";
        String email = "";

        try {

            Connection conn = DatabaseConnection.getConnection();

            // ✅ get student info + email
            String sql = "SELECT name, program, email FROM students WHERE student_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                studentName = rs.getString("name");
                program = rs.getString("program");
                email = rs.getString("email");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        PerformanceReportDAO dao = new PerformanceReportDAO();

        List<PerformanceReport> reportList = dao.getReport(studentId);
        double cgpa = dao.getCGPA(studentId);

        // ✅ Build email content
        StringBuilder message = new StringBuilder();

        message.append("Academic Performance Report\n\n");
        message.append("Name: ").append(studentName).append("\n");
        message.append("Student ID: ").append(studentId).append("\n");
        message.append("Program: ").append(program).append("\n\n");

        message.append("Results:\n");

        for(PerformanceReport r : reportList){
            message.append(r.getCourseCode())
                   .append(" - ")
                   .append(r.getCourseTitle())
                   .append(" | Grade: ")
                   .append(r.getGrade())
                   .append("\n");
        }

        message.append("\nCGPA: ").append(cgpa);

        // ✅ Send email
        EmailService.sendEmail(
                email,
                "Your Academic Performance Report",
                message.toString()
        );

        // ✅ Redirect back to report page
        response.sendRedirect("viewPerformanceReport?student_id=" + studentId);
    }
}