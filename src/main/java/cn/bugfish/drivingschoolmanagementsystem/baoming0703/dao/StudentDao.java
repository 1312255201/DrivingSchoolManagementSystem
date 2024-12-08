package cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.po.Student;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    // 使用 DBUtil 获取数据库连接
    public StudentDao() {
        System.out.println("StudentDao 已初始化");
    }

    public boolean saveStudent(Student student) throws SQLException {
        System.out.println("正在保存学生信息: " + student);

        // 使用 DBUtil 获取数据库连接
        String sql = "INSERT INTO students (name, phone, email, user_id) VALUES (?, ?, ?, ?)";

        // 获取数据库连接
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 设置参数
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getPhone());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getUserId());  // 保存用户ID

            // 执行更新操作
            int result = stmt.executeUpdate();
            if (result > 0) {
                // 获取生成的学生ID
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        student.setStudentId(rs.getInt(1));
                        System.out.println("保存成功，生成的ID: " + student.getStudentId());
                        return true;
                    }
                }
            }
            System.out.println("保存失败");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // 重新抛出异常
        }
    }

    /**
     * 更新学生的审核状态
     * @param student 学生对象，包含审核状态、拒绝原因等信息
     * @return boolean 如果更新成功返回 true，否则返回 false
     */
    public boolean updateStudentStatus(Student student) {
        String sql = "UPDATE students SET registration_status = ?, reject_reason = ?, review_time = ?, reviewer_id = ? WHERE student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置 PreparedStatement 中的参数
            ps.setString(1, student.getRegistrationStatus());
            ps.setString(2, student.getRejectReason());
            ps.setTimestamp(3, student.getReviewTime());
            ps.setString(4, student.getReviewerId());
            ps.setInt(5, student.getStudentId());

            // 执行更新操作
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 添加检查手机号是否存在的方法
    public boolean isPhoneExists(String phone) throws SQLException {
        String query = "SELECT COUNT(*) FROM students WHERE phone = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phone);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // 根据ID获取学员信息
    public Student getStudentById(int studentId) throws SQLException {
        String query = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setPhone(rs.getString("phone"));
                    student.setEmail(rs.getString("email"));
                    student.setCreatedAt(rs.getTimestamp("created_at"));
                    return student;
                }
            }
        }
        return null;
    }

    public boolean updateRegistrationStatus(int studentId, String status, String rejectReason, String reviewerId) {
        String sql = "UPDATE students SET registration_status = ?, reject_reason = ?, " +
                "review_time = CURRENT_TIMESTAMP, reviewer_id = ? WHERE student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, rejectReason);
            stmt.setString(3, reviewerId);
            stmt.setInt(4, studentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> getPendingRegistrations() {
        List<Student> pendingStudents = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE registration_status = 'PENDING'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = mapResultSetToStudent(rs);
                pendingStudents.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingStudents;
    }

    //通过状态获取学生数据
    public List<Student> getStudentsByStatus(String status) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE registration_status = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = mapResultSetToStudent(rs);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setName(rs.getString("name"));
        student.setPhone(rs.getString("phone"));
        student.setEmail(rs.getString("email"));

        // 格式化日期
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            student.setCreatedAtFormatted(sdf.format(createdAt));
        }
        student.setCreatedAt(createdAt);

        student.setRegistrationStatus(rs.getString("registration_status"));
        student.setRejectReason(rs.getString("reject_reason"));
        student.setReviewTime(rs.getTimestamp("review_time"));
        student.setReviewerId(rs.getString("reviewer_id"));

        return student;
    }

    //更新数据库为通过状态
    public static boolean updateStudentStatusToApproved(int studentId, String reviewerId) {
        String sql = "UPDATE students SET registration_status = 'APPROVED', review_time = NOW(), reviewer_id = ? WHERE student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reviewerId);
            ps.setInt(2, studentId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //更新数据库为拒绝状态
    public static boolean updateStudentStatusToRejected(int studentId, String reason, String reviewerId) {
        String sql = "UPDATE students SET registration_status = 'REJECTED', reject_reason = ?, review_time = NOW(), reviewer_id = ? WHERE student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reason);
            ps.setString(2, reviewerId);
            ps.setInt(3, studentId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     // 通过 userId 获取学生信息
    public Student getStudentByUserId(String userId) throws SQLException {
        System.out.println("正在查询用户ID为 " + userId + " 的报名信息");
        
        // 修改SQL，使用user_id关联查询
        String sql = "SELECT * FROM students WHERE user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            System.out.println("执行SQL: " + sql + ", 参数: " + userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setUserId(rs.getString("user_id"));
                    student.setName(rs.getString("name"));
                    student.setPhone(rs.getString("phone"));
                    student.setEmail(rs.getString("email"));
                    student.setCreatedAt(rs.getTimestamp("created_at"));
                    student.setRegistrationStatus(rs.getString("registration_status"));
                    student.setRejectReason(rs.getString("reject_reason"));
                    student.setReviewTime(rs.getTimestamp("review_time"));
                    
                    if (student.getCreatedAt() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        student.setCreatedAtFormatted(sdf.format(student.getCreatedAt()));
                    }
                    
                    System.out.println("找到学生信息: " + student);
                    return student;
                }
            }
        }
        System.out.println("未找到学生信息");
        return null;
    }

    // 更新学生信息
    public boolean updateStudentInfo(int studentId, String name, String phone, String email) throws SQLException {
        System.out.println("正在更新学生信息: studentId=" + studentId + ", name=" + name + ", phone=" + phone + ", email=" + email);
        
        String sql = "UPDATE students SET name = ?, phone = ?, email = ? WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection(); // 获取数据库连接
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setInt(4, studentId);
            
            int result = stmt.executeUpdate();
            System.out.println("更新结果: " + (result > 0 ? "成功" : "失败"));
            return result > 0;
        }
    }

    // 删除学生
    public boolean deleteStudent(int studentId) throws SQLException {
        System.out.println("正在删除学生信息: studentId=" + studentId);
        
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection(); // 获取数据库连接
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            
            int result = stmt.executeUpdate();
            System.out.println("删除结果: " + (result > 0 ? "成功" : "失败"));
            return result > 0;
        }
    }
}



