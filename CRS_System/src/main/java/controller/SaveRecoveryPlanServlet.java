package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import util.DatabaseConnection;

@WebServlet("/saveRecoveryPlan")
public class SaveRecoveryPlanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("student_id");
        String courseId = request.getParameter("course_id");

        String recommendation = request.getParameter("recommendations");

        String[] studyWeeks = request.getParameterValues("study_week");
        String[] tasks = request.getParameterValues("task_description");

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        int createdBy = 2; // officer default

        if(role != null && role.equals("course_admin")){
            createdBy = 1;
        }

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();

            /* ============================= */
            /* START TRANSACTION             */
            /* ============================= */

            conn.setAutoCommit(false);

            /* ============================= */
            /* Update course attempt         */
            /* ============================= */

            String updateCourseSql =
            "UPDATE student_courses " +
            "SET attempt_number = attempt_number + 1, " +
            "status = 'pending' " +
            "WHERE student_id = ? AND course_id = ?";

            PreparedStatement updatePs =
            conn.prepareStatement(updateCourseSql);

            updatePs.setString(1, studentId);
            updatePs.setString(2, courseId);

            updatePs.executeUpdate();


            /* ============================= */
            /* Insert recovery plan          */
            /* ============================= */

            String planSql =
            "INSERT INTO recovery_plans " +
            "(student_id, course_id, created_by, start_date, status) " +
            "VALUES (?, ?, ?, NOW(), 'active')";

            PreparedStatement planPs =
            conn.prepareStatement(planSql,
            PreparedStatement.RETURN_GENERATED_KEYS);

            planPs.setString(1, studentId);
            planPs.setString(2, courseId);
            planPs.setInt(3, createdBy);

            planPs.executeUpdate();

            ResultSet generatedKeys = planPs.getGeneratedKeys();

            int planId = 0;

            if(generatedKeys.next()){
                planId = generatedKeys.getInt(1);
            }


            /* ============================= */
            /* Insert recommendation task    */
            /* ============================= */

            if(recommendation != null && !recommendation.trim().isEmpty()){

                String recSql =
                "INSERT INTO recovery_tasks " +
                "(plan_id, study_week, task_description, status) " +
                "VALUES (?, ?, ?, 'recommendation')";

                PreparedStatement recPs =
                conn.prepareStatement(recSql);

                recPs.setInt(1, planId);
                recPs.setString(2, "Recommendation");
                recPs.setString(3, recommendation);

                recPs.executeUpdate();
            }


            /* ============================= */
            /* Insert milestone tasks        */
            /* ============================= */

            if(studyWeeks != null && tasks != null){

                for(int i = 0; i < studyWeeks.length; i++){

                    if(tasks[i] == null || tasks[i].trim().isEmpty())
                        continue;

                    String taskSql =
                    "INSERT INTO recovery_tasks " +
                    "(plan_id, study_week, task_description, status) " +
                    "VALUES (?, ?, ?, 'pending')";

                    PreparedStatement taskPs =
                    conn.prepareStatement(taskSql);

                    taskPs.setInt(1, planId);
                    taskPs.setString(2, studyWeeks[i]);
                    taskPs.setString(3, tasks[i]);

                    taskPs.executeUpdate();
                }
            }


            /* ============================= */
            /* COMMIT TRANSACTION            */
            /* ============================= */

            conn.commit();


        } catch(Exception e){

            try{
                if(conn != null) conn.rollback();
            } catch(Exception ex){
                ex.printStackTrace();
            }

            e.printStackTrace();
        }


        /* ============================= */
        /* Redirect after success        */
        /* ============================= */

        response.sendRedirect("manage_recovery.jsp");
    }
}