package cn.bugfish.drivingschoolmanagementsystem.ExamSystem.Listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

@WebListener
public class ExamListener implements ServletRequestListener {

    private static final Logger logger = Logger.getLogger(ExamListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String servletPath = request.getServletPath();


        if ("/exam-search".equals(servletPath)) {
            String examId = request.getParameter("id");

            if (examId != null) {
                logger.info(String.format("搜索考试请求接收: 课程ID: %s", examId));
            } else {
                logger.warning("搜索考试请求缺少必要参数: 考试ID");
            }
        }

        if ("/select-exam".equals(servletPath)) {
            Integer studentId = (Integer) request.getSession().getAttribute("userid");
            String examId = request.getParameter("exam_id");

            if (studentId != null && examId != null) {
                logger.info(String.format("选择报考请求接收: 学生ID: %d, 课程ID: %s", studentId, examId));
            } else {
                logger.warning("选择报考请求缺少必要参数: 学生ID或课程ID");
            }
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String servletPath = request.getServletPath();

        if ("/exam-search".equals(servletPath)) {
            logger.info("搜索考试请求已处理完成");
        }

        if ("/select-exam".equals(servletPath)) {
            logger.info("选择报考请求已处理完成");
        }
    }
}