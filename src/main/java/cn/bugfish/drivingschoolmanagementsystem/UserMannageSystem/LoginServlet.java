package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 验证输入是否为空
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=emptyFields");
            return;
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String query = "SELECT * FROM users WHERE phonenumber = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 登录成功，创建会话
                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("id"));
                session.setAttribute("userrole", rs.getString("role"));
                session.setAttribute("username", rs.getString("name"));
                session.setAttribute("useridnumber", rs.getString("idnumber"));
                session.setAttribute("userphonenumber", rs.getString("phonenumber"));
                session.setAttribute("useremail", rs.getString("email"));
                if ( !rs.getString("idnumber").equals("请补全个人信息"))
                {
                    session.setAttribute("avatar", "avatars/"+ rs.getString("idnumber") + ".png");
                }
                response.sendRedirect("dashboard.jsp");
            } else {
                // 登录失败，重定向到登录页面并显示错误信息
                response.sendRedirect("login.jsp?error=invalidCredentials");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=databaseError");
        } finally {
            DBUtil.closeConnection(conn);
        }
    }
}