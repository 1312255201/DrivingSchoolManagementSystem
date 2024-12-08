package cn.bugfish.drivingschoolmanagementsystem.peaplemanage.Listener;

import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Listener.UserEditListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

public class StudentChangeListener implements ServletRequestListener {
    private static final Logger logger = Logger.getLogger(UserEditListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 将 ServletRequest 转换为 HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
        // 获取请求路径

        String servletPath = httpRequest.getServletPath();
        if ("/RemoveStudentServlet".equals(servletPath)) {
            ((HttpServletRequest) sre.getServletRequest()).getSession().getAttribute("userid");
            String coachId1 = httpRequest.getParameter("coach_id");
            String studentId1 = httpRequest.getParameter("student_id");
            String teach_level1 = httpRequest.getParameter("teach_level");
            String id = httpRequest.getParameter("id");

            logger.info("编辑用户");
            logger.info("编辑用户管理员ID" + id + ",coach_id: " + coachId1 + ",student_id: " + studentId1 + ",teach_level: " + teach_level1);
        }
    }

}
