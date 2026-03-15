package dao;

import util.DatabaseConnection;
import model.PerformanceReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerformanceReportDAO {

    public List<PerformanceReport> getReport(String studentId){

        List<PerformanceReport> reportList = new ArrayList<>();

        try {

            Connection conn = DatabaseConnection.getConnection();

            String sql =
            "SELECT c.course_id, c.course_title, c.credit_hours, sc.grade, sc.grade_point " +
            "FROM student_courses sc " +
            "JOIN courses c ON sc.course_id = c.course_id " +
            "WHERE sc.student_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                PerformanceReport report = new PerformanceReport();

                report.setCourseCode(rs.getString("course_id"));
                report.setCourseTitle(rs.getString("course_title"));
                report.setCreditHours(rs.getInt("credit_hours"));
                report.setGrade(rs.getString("grade"));
                report.setGradePoint(rs.getDouble("grade_point"));

                reportList.add(report);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return reportList;
    }


    public double getCGPA(String studentId){

        double cgpa = 0;

        try{

            Connection conn = DatabaseConnection.getConnection();

            String sql =
            "SELECT ROUND(SUM(sc.grade_point * c.credit_hours) / SUM(c.credit_hours),2) AS cgpa " +
            "FROM student_courses sc " +
            "JOIN courses c ON sc.course_id = c.course_id " +
            "WHERE sc.student_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                cgpa = rs.getDouble("cgpa");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return cgpa;
    }
}