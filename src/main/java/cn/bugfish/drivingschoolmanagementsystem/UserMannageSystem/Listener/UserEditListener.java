package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

@WebListener
public class UserEditListener implements ServletRequestListener {

    private static final Logger logger = Logger.getLogger(UserEditListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 将 ServletRequest 转换为 HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
        // 获取请求路径
        String servletPath = httpRequest.getServletPath();
        if ("/EditUserServlet".equals(servletPath)) {
            ((HttpServletRequest) sre.getServletRequest()).getSession().getAttribute("userid");
            String id = httpRequest.getParameter("id");
            logger.info("编辑用户");
            logger.info("编辑用户管理员ID" + id + ",被编辑用户: " + id);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 同样将 ServletRequest 转换为 HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
        String servletPath = httpRequest.getServletPath();

        if ("/EditUserServlet".equals(servletPath)) {
            logger.info("编辑结束.");
        }
    }

}

