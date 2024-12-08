package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/download-proof")
public class DownloadProofServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestId = request.getParameter("id");

        // 查询文件路径
        String filePath = null;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT evidence_path FROM leave_requests WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(requestId));
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        filePath = rs.getString("evidence_path");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"数据库查询失败！\"}");
            return;
        }

        // 验证文件路径是否有效
        if (filePath == null || filePath.isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"文件路径不存在！\"}");
            return;
        }

        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"文件不存在！\"}");
            return;
        }

        // 设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.setContentLength((int) file.length());

        // 读取文件并写入响应
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"文件传输失败！\"}");
        }
    }
}
