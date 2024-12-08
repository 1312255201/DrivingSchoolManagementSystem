package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

@MultipartConfig
@WebServlet("/CompleteRealNameServlet")
public class CompleteRealNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userid");

        if (userId == null) {
            response.sendRedirect("login.jsp"); // 未登录
            return;
        }

        String name = request.getParameter("name");
        String idnumber = request.getParameter("idnumber");
        Part avatarPart = request.getPart("avatar");

        if (avatarPart == null || avatarPart.getSize() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "请上传证件照！");
            return;
        }

        // 验证文件类型
        String contentType = avatarPart.getContentType();
        if (!contentType.startsWith("image/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "文件类型不正确，请上传图片！");
            return;
        }

        String avatarPath = getServletContext().getRealPath("/") + "avatars/";
        Files.createDirectories(Paths.get(avatarPath)); // 确保目录存在
        String sanitizedIdNumber = idnumber.replaceAll("[^a-zA-Z0-9]", ""); // 清理文件名
        String relativeAvatarPath = "avatars/" + sanitizedIdNumber + ".png";
        avatarPart.write(avatarPath + sanitizedIdNumber + ".png"); // 保存文件

        String sql = "UPDATE users SET name = ?, idnumber = ?, finish_info = 'yes' WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, idnumber);
            pstmt.setInt(3, userId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                session.setAttribute("username", name);
                session.setAttribute("useridnumber", idnumber);
                session.setAttribute("avatar", relativeAvatarPath);
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "更新失败，请重试。");
            }
        } catch (Exception e) {
            log("Error in CompleteRealNameServlet: ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误，请稍后重试。");
        }
    }
}
