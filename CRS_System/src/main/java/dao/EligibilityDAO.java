package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Student;
import util.DatabaseConnection;

public class EligibilityDAO {

    // =========================
    // BASE QUERY (USED BY BOTH)
    // =========================
    private String baseQuery =
        "SELECT s.student_id, s.name, " +
        "ROUND(SUM(sc.grade_point * c.credit_hours) / SUM(c.credit_hours),2) AS cgpa, " +
        "SUM(CASE WHEN sc.status='failed' THEN 1 ELSE 0 END) AS failed_courses " +
        "FROM students s " +
        "JOIN student_courses sc ON s.student_id = sc.student_id " +
        "JOIN courses c ON sc.course_id = c.course_id " +
        "WHERE s.progressed = FALSE " +
        "AND s.recovery_program = FALSE " +
        "GROUP BY s.student_id ";

    // =========================
    // GET ELIGIBLE STUDENTS
    // =========================
    public List<Student> getEligibleStudents(){

        List<Student> list = new ArrayList<>();

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = baseQuery +
                    "HAVING (SUM(sc.grade_point * c.credit_hours) / SUM(c.credit_hours)) >= 2.0 " +
                    "AND SUM(CASE WHEN sc.status='failed' THEN 1 ELSE 0 END) <= 3";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Student s = new Student();

                s.setStudentId(rs.getString("student_id"));
                s.setStudentName(rs.getString("name"));
                s.setCgpa(rs.getDouble("cgpa"));
                s.setFailedCourses(rs.getInt("failed_courses"));

                list.add(s);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // GET NOT ELIGIBLE STUDENTS
    // =========================
    public List<Student> getIneligibleStudents(){

        List<Student> list = new ArrayList<>();

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql = baseQuery +
                    "HAVING (SUM(sc.grade_point * c.credit_hours) / SUM(c.credit_hours)) < 2.0 " +
                    "OR SUM(CASE WHEN sc.status='failed' THEN 1 ELSE 0 END) > 3";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Student s = new Student();

                s.setStudentId(rs.getString("student_id"));
                s.setStudentName(rs.getString("name"));
                s.setCgpa(rs.getDouble("cgpa"));
                s.setFailedCourses(rs.getInt("failed_courses"));

                list.add(s);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // REGISTER STUDENT
    // =========================
    public boolean registerStudent(String studentId){

        boolean success = false;

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql =
                    "UPDATE students " +
                    "SET year_of_study = year_of_study + 1, progressed = TRUE " +
                    "WHERE student_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);

            int rows = ps.executeUpdate();

            if(rows > 0){
                success = true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return success;
    }

    // =========================
    // ENROLL STUDENT TO RECOVERY
    // =========================
    public boolean enrollRecovery(String studentId){

        boolean success = false;

        try(Connection conn = DatabaseConnection.getConnection()){

            String sql =
                    "UPDATE students " +
                    "SET recovery_program = TRUE " +
                    "WHERE student_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);

            int rows = ps.executeUpdate();

            if(rows > 0){
                success = true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return success;
    }
}