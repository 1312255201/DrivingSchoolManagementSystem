package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Service;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Beans.UserBean;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {
    public boolean registerUser(UserBean user) throws SQLException {
        Connection conn = null;
        try {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO users (phonenumber, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getPhonenumber());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);
            return stmt.executeUpdate() > 0;
        } finally {
        }
    }
}
