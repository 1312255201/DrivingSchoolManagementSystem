package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");

        // 验证输入是否为空
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                captcha == null || captcha.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=emptyFields");
            return;
        }

        // 验证验证码
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            response.sendRedirect("login.jsp?error=invalidCaptcha");
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
                session = request.getSession();
                session.setAttribute("userid", rs.getInt("id"));
                session.setAttribute("userrole", rs.getString("role"));
                session.setAttribute("username", rs.getString("name"));
                session.setAttribute("useridnumber", rs.getString("idnumber"));
                session.setAttribute("useremail", rs.getString("email"));
                session.setAttribute("userphonenumber", rs.getString("phonenumber"));
                if ( !rs.getString("idnumber").equals("请补全个人信息"))
                {
                    session.setAttribute("avatar", "avatars/"+ rs.getString("idnumber") + ".png");
                }
                response.sendRedirect("dashboard.jsp");
            } else {
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