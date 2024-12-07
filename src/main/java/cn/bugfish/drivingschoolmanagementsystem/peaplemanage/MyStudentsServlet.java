package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/my-students")
public class MyStudentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid"); // 从会话中获取教练ID
        String filter = request.getParameter("filter"); // 获取筛选条件

        List<Map<String, String>> students = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            // 构造查询语句
            String sql = """
                SELECT 
                    u.name, 
                    u.phonenumber, 
                    t.teach_level, 
                    ss.study_type, 
                    ss.state 
                FROM teach_info t
                JOIN users u ON t.student_id = u.id
                LEFT JOIN student_state ss ON u.id = ss.student_id
                WHERE t.coach_id = ?
                """;

            // 根据筛选条件调整查询
            if ("in-progress".equals(filter)) {
                sql += " AND ss.state IN ('科目二', '科目三')";
            } else if ("passed".equals(filter)) {
                sql += " AND ((t.teach_level = '科目二' AND ss.state NOT IN ('科目二'))" +
                        " OR (t.teach_level = '科目三' AND ss.state NOT IN ('科目三')))";
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> student = new HashMap<>();
                        student.put("name", rs.getString("name"));
                        student.put("phonenumber", rs.getString("phonenumber"));
                        student.put("teachLevel", rs.getString("teach_level"));
                        student.put("state", rs.getString("state"));

                        // 添加备注信息
                        if ("科目二".equals(rs.getString("teach_level")) && !rs.getString("state").equals("科目二")) {
                            student.put("remark", "已通过");
                        } else if ("科目三".equals(rs.getString("teach_level")) && !rs.getString("state").equals("科目三")) {
                            student.put("remark", "已通过");
                        } else {
                            student.put("remark", "正在学习");
                        }

                        students.add(student);
                    }
                }
            }

            // 返回JSON数据
            response.setContentType("application/json");
            response.getWriter().write(new com.google.gson.Gson().toJson(students));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"加载学员信息失败！\"}");
        }
    }
}
