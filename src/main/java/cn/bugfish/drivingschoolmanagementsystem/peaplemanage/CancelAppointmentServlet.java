package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 * 取消预约的 Servlet
 * 该 Servlet 接收一个名为 appointment_id 的请求参数，并从数据库中删除对应的预约记录
 */
@WebServlet("/cancel-appointment")
public class CancelAppointmentServlet extends HttpServlet {
    /**
     * 处理 HTTP DELETE 请求，用于取消预约
     *
     * @param request  包含请求参数的 HttpServletRequest 对象
     * @param response 用于发送响应的 HttpServletResponse 对象
     * @throws IOException 如果在处理请求或发送响应时发生 I/O 错误
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从请求参数中获取预约 ID
        int appointmentId = Integer.parseInt(request.getParameter("appointment_id"));

        try (Connection conn = DBUtil.getConnection()) {
            // 定义 SQL 删除语句，用于从 appointment 表中删除指定 ID 的预约记录
            String sql = "DELETE FROM appointment WHERE id =?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // 设置 SQL 语句中的参数
                ps.setInt(1, appointmentId);
                // 执行 SQL 语句，删除预约记录
                int rows = ps.executeUpdate();
                // 根据删除结果，向客户端返回成功或失败的 JSON 响应
                if (rows > 0) {
                    response.getWriter().write("{\"success\": true}");
                } else {
                    response.getWriter().write("{\"success\": false}");
                }
            }
        } catch (Exception e) {
            // 打印异常堆栈跟踪
            e.printStackTrace();
            // 如果发生异常，向客户端返回失败的 JSON 响应
            response.getWriter().write("{\"success\": false}");
        }
    }
}
