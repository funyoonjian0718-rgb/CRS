package controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Student;
import util.DatabaseConnection;

@WebServlet("/createRecoveryPlan")
public class CreateRecoveryPlanServlet extends HttpServlet {

	private static final int MAX_ATTEMPT_LIMIT = 3;
	
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("student_id");

        /* ============================= */
        /* AJAX request → load courses  */
        /* ============================= */

        if(studentId != null){

            List<String> failedCourses = new ArrayList<>();

            try{

                Connection conn = DatabaseConnection.getConnection();

                String sql =
               	"SELECT course_id " +
               	"FROM student_courses " +
               	"WHERE student_id = ? " +
               	"AND status = 'failed' " +
                "AND attempt_number <= ?";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, studentId);
                ps.setInt(2, MAX_ATTEMPT_LIMIT);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    failedCourses.add(rs.getString("course_id"));
                }

            } catch(Exception e){
                e.printStackTrace();
            }

            response.setContentType("application/json");

            String json = "[";

            for(int i=0;i<failedCourses.size();i++){

                json += "\"" + failedCourses.get(i) + "\"";

                if(i < failedCourses.size()-1){
                    json += ",";
                }
            }

            json += "]";

            response.getWriter().write(json);

            return;
        }

        /* ============================= */
        /* Normal page load → students  */
        /* ============================= */

        List<Student> students = new ArrayList<>();

        try {

            Connection conn = DatabaseConnection.getConnection();

            String sql =
           	"SELECT s.student_id, s.name, " +
           	"COUNT(sc.record_id) AS failed_courses " +
           	"FROM students s " +
           	"JOIN student_courses sc " +
           	"ON s.student_id = sc.student_id " +
           	"WHERE s.recovery_program = 1 " +
          	"AND sc.status = 'failed' " +
           	"AND sc.attempt_number <= ? " +
           	"GROUP BY s.student_id, s.name " +
           	"HAVING COUNT(sc.record_id) > 0";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, MAX_ATTEMPT_LIMIT);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Student s = new Student();

                s.setStudentId(rs.getString("student_id"));
                s.setStudentName(rs.getString("name"));
                s.setFailedCourses(rs.getInt("failed_courses"));

                students.add(s);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("studentList", students);

        RequestDispatcher rd =
        request.getRequestDispatcher("create_recovery.jsp");

        rd.forward(request, response);
    }
}