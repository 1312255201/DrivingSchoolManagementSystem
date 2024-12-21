package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Loger.UserMannageSystemLoger;
import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
@WebServlet("/CompleteRealNameServlet")
public class CompleteRealNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        //获取当前会话
        HttpSession session = request.getSession();
        // 从会话中获取用户ID
        Integer userId = (Integer) session.getAttribute("userid");
        // 如果用户未登录，则重定向到登录页面
        if (userId == null) {
            response.sendRedirect("login.jsp"); // 未登录
            return;
        }
        // 从请求中获取用户提交的姓名和身份证号
        String name = request.getParameter("name");
        String idnumber = request.getParameter("idnumber");
        // 从请求中获取用户上传的头像文件
        Part avatarPart = request.getPart("avatar");
        // 如果未上传头像，则返回错误信息
        if (avatarPart == null) {
            UserMannageSystemLoger.logger.warn("未收到 avatar 部分");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"证件照上传未完成，请稍后！\"}");
            return;
        }

        if (avatarPart.getSize() == 0) {
            UserMannageSystemLoger.logger.warn("avatar 文件大小为 0");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"证件照上传未完成，请稍后！\"}");
            return;
        }

        // 验证文件类型是否为图片
        String contentType = avatarPart.getContentType();
        if (!contentType.startsWith("image/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"文件类型不正确，请上传图片！\"}");
            return;
        }
        // 获取应用程序的绝对路径，并拼接出头像文件的保存路径
        String avatarPath = getServletContext().getRealPath("/") + "avatars/";
        // 创建保存头像文件的目录（如果不存在）
        Files.createDirectories(Paths.get(avatarPath)); // 确保目录存在
        // 清理身份证号中的非法字符，用于生成文件名
        String sanitizedIdNumber = idnumber.replaceAll("[^a-zA-Z0-9]", ""); // 清理文件名
        // 生成头像文件的相对路径
        String relativeAvatarPath = "avatars/" + sanitizedIdNumber + ".png";
        // 将头像文件写入到指定目录
        avatarPart.write(avatarPath + sanitizedIdNumber + ".png"); // 保存文件
        // 定义SQL语句，用于更新用户信息
        String sql = "SELECT * FROM users WHERE idnumber = ?";
        try (Connection conn = DBUtil.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idnumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserMannageSystemLoger.logger.warn("身份证号在数据库内重复");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"身份证号已经在数据库中存在了！\"}");
                return;
            }
        } catch (SQLException e) {
            UserMannageSystemLoger.logger.error("发生错误在完成实名信息的CompleteRealNameServlet中验证身份证模块: ", e);
        }

        sql = "UPDATE users SET name = ?, idnumber = ?, finish_info = 'yes' WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             // 设置SQL语句中的参数
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, idnumber);
            pstmt.setInt(3, userId);
            // 执行SQL语句，更新用户信息
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                // 更新成功，将新的用户信息保存到会话中
                session.setAttribute("username", name);
                session.setAttribute("useridnumber", idnumber);
                session.setAttribute("avatar", relativeAvatarPath);
                // 重定向到用户主页
                response.sendRedirect("dashboard.jsp");
            } else {
                UserMannageSystemLoger.logger.warn("实名信息更新失败");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"实名信息更新失败！\"}");
            }
        } catch (Exception e) {
            // 记录错误日志
            UserMannageSystemLoger.logger.error("发生错误在完成实名信息的CompleteRealNameServlet中: ", e);
            // 返回服务器内部错误信息
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"服务器内部错误，请稍后重试！\"}");
        }
    }
}
