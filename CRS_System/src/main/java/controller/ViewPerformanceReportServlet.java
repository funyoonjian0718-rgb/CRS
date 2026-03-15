package controller;

import dao.PerformanceReportDAO;
import model.PerformanceReport;
import util.DatabaseConnection;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/viewPerformanceReport")
public class ViewPerformanceReportServlet extends HttpServlet {

    private PerformanceReportDAO reportDAO;

    public void init(){
        reportDAO = new PerformanceReportDAO();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("student_id");

        String studentName = "";
        String program = "";

        try{

            Connection conn = DatabaseConnection.getConnection();

            String sql =
            "SELECT name, program FROM students WHERE student_id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                studentName = rs.getString("name");
                program = rs.getString("program");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        List<PerformanceReport> reportList =
                reportDAO.getReport(studentId);

        double cgpa = reportDAO.getCGPA(studentId);

        request.setAttribute("studentId", studentId);
        request.setAttribute("studentName", studentName);
        request.setAttribute("program", program);
        request.setAttribute("reportList", reportList);
        request.setAttribute("cgpa", cgpa);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("performance_report.jsp");

        dispatcher.forward(request,response);
    }
}