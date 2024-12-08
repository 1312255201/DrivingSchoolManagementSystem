package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.DAO;


import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Model.EmailConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmailConfigDAO {
    public static EmailConfig getEmailConfig() throws Exception {
        String query = "SELECT email_address, email_password, smtp_host, smtp_port, is_ssl_enabled FROM email_config LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                EmailConfig config = new EmailConfig();
                config.setEmailAddress(rs.getString("email_address"));
                config.setEmailPassword(rs.getString("email_password"));
                config.setSmtpHost(rs.getString("smtp_host"));
                config.setSmtpPort(rs.getInt("smtp_port"));
                config.setSslEnabled(rs.getBoolean("is_ssl_enabled"));
                return config;
            } else {
                throw new Exception("邮箱配置未找到！");
            }
        }
    }
}
