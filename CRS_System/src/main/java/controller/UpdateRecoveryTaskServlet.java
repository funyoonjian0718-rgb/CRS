package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.DatabaseConnection;

@WebServlet("/updateRecoveryTasks")
public class UpdateRecoveryTaskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder json = new StringBuilder();

        BufferedReader reader = request.getReader();
        String line;

        while((line = reader.readLine()) != null){
            json.append(line);
        }

        String body = json.toString();

        try{

            Connection conn = DatabaseConnection.getConnection();

            /* ============================= */
            /* Extract fields from JSON */
            /* ============================= */

            String planId =
            body.split("\"plan_id\":")[1].split(",")[0]
            .replace("\"","").trim();

            String recommendation =
            body.split("\"recommendation\":\"")[1].split("\"")[0];

            String courseStatus =
            body.split("\"course_status\":\"")[1].split("\"")[0];

            String planStatus =
            body.split("\"plan_status\":\"")[1].split("\"")[0];


            /* ============================= */
            /* Update recommendation */
            /* ============================= */

            String updateRec =
            "UPDATE recovery_tasks " +
            "SET task_description=? " +
            "WHERE plan_id=? AND status='recommendation'";

            PreparedStatement psRec =
            conn.prepareStatement(updateRec);

            psRec.setString(1, recommendation);
            psRec.setString(2, planId);

            psRec.executeUpdate();


            /* ============================= */
            /* Update course status */
            /* ============================= */

            String updateCourse =
            "UPDATE student_courses sc " +
            "JOIN recovery_plans rp " +
            "ON sc.student_id = rp.student_id " +
            "AND sc.course_id = rp.course_id " +
            "SET sc.status=? " +
            "WHERE rp.plan_id=?";

            PreparedStatement psCourse =
            conn.prepareStatement(updateCourse);

            psCourse.setString(1, courseStatus);
            psCourse.setString(2, planId);

            psCourse.executeUpdate();


            /* ============================= */
            /* Update recovery plan status */
            /* ============================= */

            String updatePlan =
            "UPDATE recovery_plans SET status=? WHERE plan_id=?";

            PreparedStatement psPlan =
            conn.prepareStatement(updatePlan);

            psPlan.setString(1, planStatus);
            psPlan.setString(2, planId);

            psPlan.executeUpdate();


            /* ============================= */
            /* Get task IDs */
            /* ============================= */

            List<Integer> taskIds = new ArrayList<>();

            String selectTasks =
            "SELECT task_id FROM recovery_tasks " +
            "WHERE plan_id=? AND status!='recommendation' " +
            "ORDER BY task_id";

            PreparedStatement psSelect =
            conn.prepareStatement(selectTasks);

            psSelect.setString(1, planId);

            ResultSet rs = psSelect.executeQuery();

            while(rs.next()){
                taskIds.add(rs.getInt("task_id"));
            }


            /* ============================= */
            /* Extract tasks JSON */
            /* ============================= */

            String tasksPart =
            body.split("\"tasks\":\\[")[1].split("\\]")[0];

            String[] taskArray =
            tasksPart.split("\\},\\{");


            /* ============================= */
            /* Update tasks */
            /* ============================= */

            for(int i=0;i<taskArray.length && i<taskIds.size();i++){

                String t = taskArray[i];

                t = t.replace("{","").replace("}","");

                String week =
                t.split("\"study_week\":\"")[1].split("\"")[0];

                String desc =
                t.split("\"task_description\":\"")[1].split("\"")[0];

                String status =
                t.split("\"status\":\"")[1].split("\"")[0];

                String updateTask =
                "UPDATE recovery_tasks " +
                "SET study_week=?, task_description=?, status=? " +
                "WHERE task_id=?";

                PreparedStatement psUpdate =
                conn.prepareStatement(updateTask);

                psUpdate.setString(1, week);
                psUpdate.setString(2, desc);
                psUpdate.setString(3, status);
                psUpdate.setInt(4, taskIds.get(i));

                psUpdate.executeUpdate();
            }

            response.getWriter().write("success");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}