package cn.bugfish.drivingschoolmanagementsystem.CurriculumSystem.Listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

@WebListener
public class CourseListener implements ServletRequestListener {

    private static final Logger logger = Logger.getLogger(CourseListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String servletPath = request.getServletPath();

        if ("/cancel-course".equals(servletPath)) {
            Integer studentId = (Integer) request.getSession().getAttribute("userid");
            String courseId = request.getParameter("course_id");

            if (studentId != null && courseId != null) {
                logger.info(String.format("课程取消请求接收: 学生ID: %d, 课程ID: %s", studentId, courseId));
            } else {
                logger.warning("课程取消请求缺少必要参数: 学生ID或课程ID");
            }
        }

        if ("/delete-course".equals(servletPath)) {
            String courseId = request.getParameter("id");

            if (courseId != null) {
                logger.info(String.format("课程删除请求接收: 课程ID: %s", courseId));
            } else {
                logger.warning("课程删除请求缺少必要参数: 课程ID");
            }
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String servletPath = request.getServletPath();

        if ("/cancel-course".equals(servletPath)) {
            logger.info("课程取消请求已处理完成");
        }

        if ("/delete-course".equals(servletPath)) {
            logger.info("课程删除请求已处理完成");
        }
    }
}