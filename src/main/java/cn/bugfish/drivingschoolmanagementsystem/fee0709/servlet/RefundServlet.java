package cn.bugfish.drivingschoolmanagementsystem.fee0709.servlet;

import cn.bugfish.drivingschoolmanagementsystem.fee0709.po.Refund;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.service.RefundService;
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

@WebServlet("/refund/*")
public class RefundServlet extends HttpServlet {
    private RefundService refundService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.refundService = new RefundService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo();
            if ("/create".equals(pathInfo)) {
                // 获取表单数据
                int paymentId = Integer.parseInt(request.getParameter("paymentId"));
                BigDecimal refundAmount = new BigDecimal(request.getParameter("refundAmount"));
                String refundReason = request.getParameter("refundReason");

                System.out.println("收到退款申请 - PaymentID: " + paymentId + 
                                 ", Amount: " + refundAmount + 
                                 ", Reason: " + refundReason);

                // 创建退款记录
                boolean success = refundService.createRefund(paymentId, refundAmount, refundReason);

                if (success) {
                    out.write("{\"success\":true,\"message\":\"退款申请已提交\"}");
                } else {
                    out.write("{\"success\":false,\"message\":\"退款申请提交失败\"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String pathInfo = request.getPathInfo();
            if ("/list".equals(pathInfo)) {
                List<Refund> refunds = refundService.getAllRefunds();
                out.write(gson.toJson(refunds));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}