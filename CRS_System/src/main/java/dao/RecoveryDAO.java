package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import util.DatabaseConnection;

public class RecoveryDAO {

    public boolean enrollStudent(String studentId){

        boolean success = false;

        try{

            Connection conn = DatabaseConnection.getConnection();

            String sql =
            "UPDATE students SET recovery_program = TRUE WHERE student_id = ?";

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