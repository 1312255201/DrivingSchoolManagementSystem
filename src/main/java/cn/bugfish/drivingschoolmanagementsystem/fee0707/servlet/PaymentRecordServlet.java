package cn.bugfish.drivingschoolmanagementsystem.fee0707.servlet;

import cn.bugfish.drivingschoolmanagementsystem.fee0707.po.PaymentRecord;
import cn.bugfish.drivingschoolmanagementsystem.fee0707.service.PaymentRecordService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/payment/*")
public class PaymentRecordServlet extends HttpServlet {
    private PaymentRecordService paymentRecordService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.paymentRecordService = new PaymentRecordService();
        this.gson = new Gson();
        System.out.println("PaymentRecordServlet 已初始化");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo();
        System.out.println("GET请求路径: " + pathInfo);

        try {
            if ("/list".equals(pathInfo)) {
                System.out.println("获取所有支付记录");
                List<PaymentRecord> records = paymentRecordService.getAllPaymentRecords();
                String json = gson.toJson(records);
                System.out.println("返回的JSON数据: " + json);
                out.write(json);
            } else if (pathInfo != null && pathInfo.startsWith("/student/")) {
                int studentId = Integer.parseInt(pathInfo.substring("/student/".length()));
                System.out.println("获取学员ID为 " + studentId + " 的支付记录");
                List<PaymentRecord> records = paymentRecordService.getPaymentRecordsByStudentId(studentId);
                String json = gson.toJson(records);
                System.out.println("返回的JSON数据: " + json);
                out.write(json);
            } else {
                System.out.println("未找到匹配的路径: " + pathInfo);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("处理请求时发生错误: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        System.out.println("POST请求路径: " + pathInfo);

        try {
            if ("/create".equals(pathInfo)) {
                // 获取请求参数
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                int feeId = Integer.parseInt(request.getParameter("feeId"));
                BigDecimal amount = new BigDecimal(request.getParameter("amount"));
                String paymentMethod = request.getParameter("paymentMethod");

                System.out.println("创建支付记录 - 学员ID: " + studentId + ", 费用ID: " + feeId + 
                                 ", 金额: " + amount + ", 支付方式: " + paymentMethod);

                boolean success = paymentRecordService.createPaymentRecord(
                    studentId, feeId, amount, paymentMethod);

                if (success) {
                    out.write("{\"success\":true,\"message\":\"支付记录创建成功\"}");
                } else {
                    out.write("{\"success\":false,\"message\":\"支付记录创建失败\"}");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("处理支付请求时发生错误: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}