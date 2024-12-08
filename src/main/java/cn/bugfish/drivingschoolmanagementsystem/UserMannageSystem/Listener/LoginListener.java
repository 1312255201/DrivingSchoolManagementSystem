package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理监听器，用于监听用户会话的创建和销毁事件
 * 并且记录系统日志
 */


@WebListener
public class LoginListener implements HttpSessionListener, HttpSessionAttributeListener {
    private static final Logger logger = LogManager.getLogger(LoginListener.class);
    // 获取在线用户列表
    // 用于存储当前在线的用户
    @Getter
    private static final ConcurrentHashMap<String, Integer> onlineUsers = new ConcurrentHashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("会话已创建: " + event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        Integer username = onlineUsers.remove(sessionId);
        if (username != null) {
            logger.info("用户已注销: " + username);
        }
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("userid".equals(event.getName())) {
            Integer username = (Integer) event.getValue();
            onlineUsers.put(event.getSession().getId(), username);
            logger.info("用户登录: " + username);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("userid".equals(event.getName())) {
            Integer username = onlineUsers.remove(event.getSession().getId());
            if (username != null) {
                logger.info("用户注销: " + username);
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if ("userid".equals(event.getName())) {
            String oldUsername = (String) event.getValue();
            Integer newUsername = (Integer) event.getSession().getAttribute("userid");
            onlineUsers.put(event.getSession().getId(), newUsername);
            logger.info("用户切换: " + oldUsername + " -> " + newUsername);
        }
    }

}
