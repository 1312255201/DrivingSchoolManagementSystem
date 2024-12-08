package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/available-schedules")
public class AvailableSchedulesServlet extends HttpServlet {
    /**
     * 处理 HTTP GET 请求，用于获取可用课程安排
     *
     * @param request  包含请求参数的 HttpServletRequest 对象
     * @param response 用于发送响应的 HttpServletResponse 对象
     * @throws IOException 如果在处理请求或发送响应时发生 I/O 错误
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection conn = DBUtil.getConnection()) {
            // 定义 SQL 查询语句，用于从 schedule 表和 appointment 表中获取可用课程安排的详细信息
            String sql = """
                SELECT s.id, s.date, s.time, 
                       (s.capacity - COUNT(a.id)) AS remaining
                FROM schedule s
                LEFT JOIN appointment a ON s.id = a.schedule_id
                GROUP BY s.id
                HAVING remaining > 0
                """;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // 创建一个 List 来存储课程安排的详细信息
                List<Map<String, Object>> schedules = new ArrayList<>();
                // 遍历查询结果集，将每个课程安排的详细信息存储到 Map 中，并添加到 List 中
                while (rs.next()) {
                    Map<String, Object> schedule = new HashMap<>();
                    schedule.put("id", rs.getInt("id"));
                    schedule.put("date", rs.getString("date"));
                    schedule.put("time", rs.getString("time"));
                    schedule.put("remaining", rs.getInt("remaining"));
                    schedules.add(schedule);
                }
                // 设置响应的内容类型为 JSON，并指定字符编码为 UTF-8
                response.setContentType("application/json");
                // 获取响应的输出流，并将课程安排的详细信息以 JSON 格式写入到响应中
                response.getWriter().write(new Gson().toJson(schedules));
            }
        } catch (Exception e) {
            //打印异常堆栈跟踪
            e.printStackTrace();
        }
    }
}
