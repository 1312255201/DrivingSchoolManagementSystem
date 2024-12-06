package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem;

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
        Part avatarPart = request.getPart("avatar"); // 获取上传的文件

        if (avatarPart == null || avatarPart.getSize() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "请上传证件照！");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            // 保存证件照到 ./avatars/{身份证号}.png
            String avatarPath = getServletContext().getRealPath("/") + "avatars/";
            Files.createDirectories(Paths.get(avatarPath)); // 创建目录
            String avatarFileName = avatarPath + idnumber + ".png";
            avatarPart.write(avatarFileName);

            // 更新数据库
            String sql = "UPDATE users SET name = ?, idnumber = ?, finish_info = 'yes' WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, idnumber);
            pstmt.setInt(3, userId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                session.setAttribute("username",name);
                session.setAttribute("useridnumber", idnumber);
                session.setAttribute("avatar", "avatars/"+ idnumber + ".png");
                response.sendRedirect("dashboard.jsp"); // 更新成功后跳转到主页面
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "更新失败，请重试。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器错误，请稍后重试。");
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}