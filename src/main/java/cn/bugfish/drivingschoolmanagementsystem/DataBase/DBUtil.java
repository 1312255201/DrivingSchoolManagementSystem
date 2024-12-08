package cn.bugfish.drivingschoolmanagementsystem.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 */

public class DBUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/drivingschool?useSSL=false&characterEncoding=utf8";
    private static final String JDBC_USER = "drivingschool";
    private static final String JDBC_PASSWORD = "drivingschool"; // 替换为你的数据库密码

    static {
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("加载数据库驱动失败: " + e.getMessage());
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    /**
     * 关闭数据库连接
     * @param conn Connection 对象
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("关闭数据库连接失败: " + e.getMessage());
            }
        }
    }
}