package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/admin-review-leave-request")
public class AdminReviewLeaveRequestServlet extends HttpServlet {
    /**
     * 处理 HTTP POST 请求，用于管理员审核请假请求
     *
     * @param request  包含请求参数的 HttpServletRequest 对象
     * @param response 用于发送响应的 HttpServletResponse 对象
     * @throws IOException 如果在处理请求或发送响应时发生 I/O 错误
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从会话中获取管理员ID
        int adminId = (int) request.getSession().getAttribute("userid");
        // 从请求参数中获取请假请求ID
        int requestId = Integer.parseInt(request.getParameter("id"));
        // 从请求参数中获取审核操作（批准或拒绝）
        String action = request.getParameter("action");
        // 从请求参数中获取审核意见
        String comments = request.getParameter("comments");

        try (Connection conn = DBUtil.getConnection()) {
            // 定义SQL语句，用于更新请假请求的状态、审核意见、审核管理员ID和审核时间
            String sql = """
                UPDATE leave_requests
                SET status =?, review_comments =?, admin_id =?, reviewed_at = CURRENT_TIMESTAMP
                WHERE id =?
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // 设置SQL语句中的参数
                ps.setString(1, action.equals("approved")? "已批准" : "已拒绝");
                ps.setString(2, comments);
                ps.setInt(3, adminId);
                ps.setInt(4, requestId);
                // 执行SQL语句，更新请假请求的状态
                int rowsUpdated = ps.executeUpdate();
                // 设置响应的内容类型为JSON
                response.setContentType("application/json");
                // 根据更新结果，向客户端返回成功或失败的JSON响应
                response.getWriter().write(rowsUpdated > 0
                        ? "{\"success\": true, \"message\": \"审核完成！\"}"
                        : "{\"success\": false, \"message\": \"审核失败，请稍后再试！\"}");
            }
        } catch (Exception e) {
            // 打印异常堆栈跟踪
            e.printStackTrace();
            // 向客户端返回服务器错误的JSON响应
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误，请稍后再试！\"}");
        }
    }
}
