package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/add-schedule")
public class AddScheduleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从会话中获取教练ID
        try{
            int coachId = (int) request.getSession().getAttribute("userid");
            // 从请求参数中获取日期
            String date = request.getParameter("date");
            // 从请求参数中获取时间
            String time = request.getParameter("time");

            try (Connection conn = DBUtil.getConnection()) {
                // 定义SQL语句，用于插入新的课程安排
                String sql = "INSERT INTO schedule (coach_id, date, time) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    // 设置SQL语句中的参数
                    ps.setInt(1, coachId);
                    ps.setString(2, date);
                    ps.setString(3, time);
                    // 执行SQL语句，插入新的课程安排
                    ps.executeUpdate();
                    // 向客户端返回成功的JSON响应
                    response.getWriter().write("{\"success\": true}");
                }
            }
            catch (Exception e) {
                // 打印异常堆栈跟踪
                e.printStackTrace();
                // 向客户端返回失败的JSON响应
                response.getWriter().write("{\"success\": false}");
            }
        }
        catch (Exception e){
            // 打印异常堆栈跟踪
            e.printStackTrace();
            // 向客户端返回失败的JSON响应
            response.getWriter().write("{\"success\": false}");
        }
    }
}
