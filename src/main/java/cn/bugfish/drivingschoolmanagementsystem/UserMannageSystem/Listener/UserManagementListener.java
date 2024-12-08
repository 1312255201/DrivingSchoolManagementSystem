package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 用户管理监听器，用于监听用户会话的创建和销毁事件
 * 并且记录系统日志
 */

@WebListener
public class UserManagementListener implements ServletContextListener, HttpSessionListener {

    private static final Logger logger = LogManager.getLogger(UserManagementListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("系统启动 - 系统已初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("系统关闭 - 系统已关闭");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        logger.info("用户会话创建 - Session ID: {}", sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        logger.info("用户会话销毁 - Session ID: {}", sessionId);
    }
}