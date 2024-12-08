package cn.bugfish.drivingschoolmanagementsystem.peaplemanage.Listener;

import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Listener.UserEditListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;
/**
 * 学生变更监听器，用于监听学生相关请求的初始化和销毁事件
 * 并记录系统日志
 */
@WebListener
public class StudentChangeListener implements ServletRequestListener {
    private static final Logger logger = Logger.getLogger(UserEditListener.class.getName());
    /**
     * 请求初始化时调用的方法
     * @param sre 请求事件对象
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 将 ServletRequest 转换为 HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
        // 获取请求路径
        String servletPath = httpRequest.getServletPath();
        if ("/RemoveStudentServlet".equals(servletPath)) {
            // 获取管理员ID
            ((HttpServletRequest) sre.getServletRequest()).getSession().getAttribute("userid");
            // 获取教练ID
            String coachId1 = httpRequest.getParameter("coach_id");
            // 获取学生ID
            String studentId1 = httpRequest.getParameter("student_id");
            // 获取教学水平
            String teach_level1 = httpRequest.getParameter("teach_level");
            // 获取ID
            String id = httpRequest.getParameter("id");
            logger.info("编辑用户");
            // 记录日志，包括管理员ID、教练ID、学生ID、教学水平和ID
            logger.info("编辑用户管理员ID" + id + ",coach_id: " + coachId1 + ",student_id: " + studentId1 + ",teach_level: " + teach_level1);
        }
    }

}
