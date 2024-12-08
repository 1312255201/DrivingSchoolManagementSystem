package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;


import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

@MultipartConfig
@WebServlet("/submit-leave-request")
public class SubmitLeaveRequestServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "./proves/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int coachid = (int) request.getSession().getAttribute("userid");
        String reason = request.getParameter("reason");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        Part evidence = request.getPart("evidence");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            // 插入请假记录，获取生成的ID
            String sql = "INSERT INTO leave_requests (coach_id,reason, start_date, end_date, status, created_at) VALUES (?,?, ?, ?, '待审核', NOW())";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, coachid);
            ps.setString(2, reason);
            ps.setString(3, startDate);
            ps.setString(4, endDate);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("失败");
            }
            int requestId = rs.getInt(1);

            // 保存文件
            String fileName = null;
            if (evidence != null && evidence.getSize() > 0) {
                String originalFileName = evidence.getSubmittedFileName();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName = requestId + fileExtension;

                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                File file = new File(uploadDir, fileName);
                evidence.write(file.getAbsolutePath());
            }

            // 更新请假记录中的文件路径
            if (fileName != null) {
                String updateSql = "UPDATE leave_requests SET evidence_path = ? WHERE id = ?";
                ps = conn.prepareStatement(updateSql);
                ps.setString(1, UPLOAD_DIR + fileName);
                ps.setInt(2, requestId);
                ps.executeUpdate();
            }

            response.getWriter().write("{\"success\": true, \"message\": \"请假申请提交成功！\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"请假申请提交失败！\"}");
        } finally {
        }
    }
}
