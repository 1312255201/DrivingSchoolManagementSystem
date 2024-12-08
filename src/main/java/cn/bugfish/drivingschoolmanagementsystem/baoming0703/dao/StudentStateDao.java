package cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.po.StudentState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentStateDao {
    private Connection connection;

    public StudentStateDao() throws SQLException {
        this.connection = DBUtil.getConnection();
    }

    public boolean saveStudentState(StudentState studentState) {
        String sql = "INSERT INTO student_state (student_id, state, study_type) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentState.getStudentId());
            stmt.setString(2, studentState.getState());
            stmt.setString(3, studentState.getStudyType());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
