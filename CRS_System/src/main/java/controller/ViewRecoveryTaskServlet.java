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

import util.DatabaseConnection;

@WebServlet("/viewRecoveryTasks")
public class ViewRecoveryTaskServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("student_id");
        String planId = request.getParameter("plan_id");


        /* ============================= */
        /* Load plans for selected student */
        /* ============================= */

        if(studentId != null && planId == null){

            List<String> plans = new ArrayList<>();

            try{

                Connection conn = DatabaseConnection.getConnection();

                String sql =
                "SELECT plan_id, course_id, start_date " +
                "FROM recovery_plans " +
                "WHERE student_id = ? AND status = 'active'";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, studentId);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){

                    String entry =
                    rs.getInt("plan_id") + "|" +
                    rs.getString("course_id") + "|" +
                    rs.getDate("start_date");

                    plans.add(entry);
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            response.setContentType("application/json");

            String json = "[";

            for(int i=0;i<plans.size();i++){

                json += "\"" + plans.get(i) + "\"";

                if(i < plans.size()-1)
                    json += ",";
            }

            json += "]";

            response.getWriter().write(json);

            return;
        }


        /* ============================= */
        /* Load tasks for selected plan */
        /* ============================= */

        if(planId != null){

            List<String> tasks = new ArrayList<>();

            int completed = 0;
            int total = 0;

            String courseStatus = "pending";
            String planStatus = "active";

            try{

                Connection conn = DatabaseConnection.getConnection();


                /* ============================= */
                /* Get course status */
                /* ============================= */

                String statusSql =
                "SELECT sc.status " +
                "FROM student_courses sc " +
                "JOIN recovery_plans rp " +
                "ON sc.student_id = rp.student_id " +
                "AND sc.course_id = rp.course_id " +
                "WHERE rp.plan_id = ?";

                PreparedStatement statusPs =
                conn.prepareStatement(statusSql);

                statusPs.setString(1, planId);

                ResultSet statusRs = statusPs.executeQuery();

                if(statusRs.next()){
                    courseStatus = statusRs.getString("status");
                }


                /* ============================= */
                /* Get plan status */
                /* ============================= */

                String planSql =
                "SELECT status FROM recovery_plans WHERE plan_id=?";

                PreparedStatement planPs =
                conn.prepareStatement(planSql);

                planPs.setString(1, planId);

                ResultSet planRs = planPs.executeQuery();

                if(planRs.next()){
                    planStatus = planRs.getString("status");
                }


                /* ============================= */
                /* Load recovery tasks */
                /* ============================= */

                String sql =
                "SELECT task_id, study_week, task_description, status " +
                "FROM recovery_tasks " +
                "WHERE plan_id = ?";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, planId);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){

                    String status = rs.getString("status");

                    if(!"recommendation".equals(status)){

                        total++;

                        if("completed".equals(status)){
                            completed++;
                        }

                    }

                    String row =
                    rs.getInt("task_id") + "|" +
                    rs.getString("study_week") + "|" +
                    rs.getString("task_description") + "|" +
                    rs.getString("status");

                    tasks.add(row);
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            response.setContentType("application/json");

            String json =
            "{ \"progress\":\"" + completed + "/" + total + "\", " +
            "\"course_status\":\"" + courseStatus + "\", " +
            "\"plan_status\":\"" + planStatus + "\", " +
            "\"tasks\":[";

            for(int i=0;i<tasks.size();i++){

                json += "\"" + tasks.get(i) + "\"";

                if(i < tasks.size()-1)
                    json += ",";
            }

            json += "]}";

            response.getWriter().write(json);

            return;
        }


        /* ============================= */
        /* Initial page load (students) */
        /* ============================= */

        List<String> students = new ArrayList<>();

        try{

            Connection conn = DatabaseConnection.getConnection();

            String sql =
            "SELECT s.student_id, s.name, COUNT(r.plan_id) AS active_plans " +
            "FROM students s " +
            "JOIN recovery_plans r ON s.student_id = r.student_id " +
            "WHERE r.status = 'active' " +
            "GROUP BY s.student_id, s.name";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                String entry =
                rs.getString("student_id") + "|" +
                rs.getString("name") + "|" +
                rs.getInt("active_plans");

                students.add(entry);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("studentList", students);

        RequestDispatcher rd =
        request.getRequestDispatcher("track_recovery.jsp");

        rd.forward(request, response);
    }
}