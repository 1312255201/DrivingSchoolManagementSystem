package cn.bugfish.drivingschoolmanagementsystem.fee0707.servlet;

import cn.bugfish.drivingschoolmanagementsystem.fee0707.po.Fee;
import cn.bugfish.drivingschoolmanagementsystem.fee0707.service.FeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/fee/*")
public class FeeServlet extends HttpServlet {
    private FeeService feeService;

    @Override
    public void init() throws ServletException {
        this.feeService = new FeeService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        String pathInfo = request.getPathInfo();
        System.out.println("请求路径: " + pathInfo);
        
        if ("/add".equals(pathInfo)) {
            handleAddFee(request, response);
        } else if ("/update".equals(pathInfo)) {
            handleUpdateFee(request, response);
        } else if ("/delete".equals(pathInfo)) {
            handleDeleteFee(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleAddFee(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            // 获取参数
            String feeType = request.getParameter("feeType");
            String amountStr = request.getParameter("amount");
            String isInstallmentStr = request.getParameter("isInstallment");
            String installmentCountStr = request.getParameter("installmentCount");
            String paymentOptions = request.getParameter("paymentOptions");

            // 参数验证
            if (feeType == null || feeType.trim().isEmpty() || amountStr == null || amountStr.trim().isEmpty()) {
                throw new IllegalArgumentException("费用类型和金额不能为空");
            }

            // 转换参数
            BigDecimal amount;
            try {
                amount = new BigDecimal(amountStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("金额格式不正确");
            }

            boolean isInstallment = Boolean.parseBoolean(isInstallmentStr);
            int installmentCount = 1;
            if (isInstallment && installmentCountStr != null && !installmentCountStr.trim().isEmpty()) {
                try {
                    installmentCount = Integer.parseInt(installmentCountStr);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("分期期数必须是有效的整数");
                }
            }

            // 调用service层
            boolean success = feeService.addFee(feeType, amount, isInstallment, installmentCount, paymentOptions);

            // 返回结果
            String jsonResponse;
            if (success) {
                jsonResponse = "{\"success\":true,\"message\":\"费用添加成功\"}";
            } else {
                jsonResponse = "{\"success\":false,\"message\":\"费用添加失败\"}";
            }
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage() != null ? e.getMessage() : "未知错误";
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"message\":\"" + errorMessage + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if ("/list".equals(pathInfo)) {
            handleGetFees(request, response);
        } else if (pathInfo.startsWith("/get/")) {
            handleGetFee(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleGetFees(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            List<Fee> fees = feeService.getAllFees();
            
            // 使用StringBuilder构建JSON数组
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            
            for (int i = 0; i < fees.size(); i++) {
                Fee fee = fees.get(i);
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                
                // 转义特殊字符
                String escapedFeeType = escapeJsonString(fee.getFeeType());
                String escapedPaymentOptions = escapeJsonString(fee.getPaymentOptions());
                String formattedDate = fee.getCreatedAt() != null ? 
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fee.getCreatedAt()) : "";
                
                jsonBuilder.append("{")
                    .append("\"feeId\":").append(fee.getId()).append(",")
                    .append("\"feeType\":\"").append(escapedFeeType).append("\",")
                    .append("\"amount\":").append(fee.getAmount()).append(",")
                    .append("\"isInstallment\":").append(fee.isInstallment()).append(",")
                    .append("\"installmentCount\":").append(fee.getInstallmentCount()).append(",")
                    .append("\"paymentOptions\":\"").append(escapedPaymentOptions).append("\",")
                    .append("\"createdAt\":\"").append(formattedDate).append("\"")
                    .append("}");
            }
            
            jsonBuilder.append("]");
            
            System.out.println("Generated JSON: " + jsonBuilder.toString()); // 调试输出
            response.getWriter().write(jsonBuilder.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + escapeJsonString(e.getMessage()) + "\"}");
        }
    }

    // 辅助方法：转义JSON字符串
    private String escapeJsonString(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    escaped.append(c);
            }
        }
        return escaped.toString();
    }

    

    private void handleGetFee(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            int feeId = Integer.parseInt(request.getPathInfo().substring(5));
            Fee fee = feeService.getFeeById(feeId);
            
            if (fee != null) {
                // 构建JSON响应
                StringBuilder jsonBuilder = new StringBuilder();
                jsonBuilder.append("{")
                    .append("\"feeId\":").append(fee.getId()).append(",")
                    .append("\"feeType\":\"").append(escapeJsonString(fee.getFeeType())).append("\",")
                    .append("\"amount\":").append(fee.getAmount()).append(",")
                    .append("\"isInstallment\":").append(fee.isInstallment()).append(",")
                    .append("\"installmentCount\":").append(fee.getInstallmentCount()).append(",")
                    .append("\"paymentOptions\":\"").append(escapeJsonString(fee.getPaymentOptions())).append("\",")
                    .append("\"createdAt\":\"").append(fee.getCreatedAt()).append("\"")
                    .append("}");
                
                response.getWriter().write(jsonBuilder.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"费用不存在\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + escapeJsonString(e.getMessage()) + "\"}");
        }
    }

    private void handleUpdateFee(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // 获取请求参数
            String feeIdStr = request.getParameter("feeId");
            String feeType = request.getParameter("feeType");
            String amountStr = request.getParameter("amount");
            String isInstallmentStr = request.getParameter("isInstallment");
            String installmentCountStr = request.getParameter("installmentCount");
            String paymentOptions = request.getParameter("paymentOptions");

            // 打印接收到的原始数据
            System.out.println("接收到的原始数据:");
            System.out.println("feeIdStr: " + feeIdStr);
            System.out.println("feeType: " + feeType);
            System.out.println("amountStr: " + amountStr);
            System.out.println("isInstallmentStr: " + isInstallmentStr);
            System.out.println("installmentCountStr: " + installmentCountStr);
            System.out.println("paymentOptions: " + paymentOptions);

            // 数据转换和验证
            if (feeType == null || feeType.trim().isEmpty()) {
                throw new IllegalArgumentException("费用类型不能为空");
            }

            BigDecimal amount;
            try {
                amount = new BigDecimal(amountStr);
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("金额必须大于0");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("金额格式不正确");
            }

            boolean isInstallment = Boolean.parseBoolean(isInstallmentStr);
            int installmentCount = 1;
            if (isInstallment) {
                try {
                    installmentCount = Integer.parseInt(installmentCountStr);
                    if (installmentCount < 2) {
                        throw new IllegalArgumentException("分期期数必须大于1");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("分期期数格式不正确");
                }
            }

            // 执行操作
            boolean success;
            try {
                if (feeIdStr == null || feeIdStr.trim().isEmpty()) {
                    System.out.println("执行新增操作");
                    success = feeService.addFee(feeType, amount, isInstallment, installmentCount, paymentOptions);
                } else {
                    System.out.println("执行更新操作");
                    int feeId = Integer.parseInt(feeIdStr);
                    success = feeService.updateFee(feeId, feeType, amount, isInstallment, installmentCount, paymentOptions);
                }

                if (success) {
                    out.write("{\"success\":true,\"message\":\"费用操作成功\"}");
                } else {
                    out.write("{\"success\":false,\"message\":\"费用操作失败，请检查数据库连接或数据格式\"}");
                }
            } catch (Exception e) {
                System.err.println("数据库操作失败: " + e.getMessage());
                e.printStackTrace();
                out.write("{\"success\":false,\"message\":\"数据库操作失败：" + e.getMessage() + "\"}");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("参数验证失败: " + e.getMessage());
            out.write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            System.err.println("系统错误: " + e.getMessage());
            e.printStackTrace();
            out.write("{\"success\":false,\"message\":\"系统错误：" + e.getMessage() + "\"}");
        }
    }

    private void handleDeleteFee(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            String feeIdStr = request.getParameter("feeId");
            if (feeIdStr == null || feeIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("费用ID不能为空");
            }
            
            int feeId = Integer.parseInt(feeIdStr);
            System.out.println("正在删除费用，ID: " + feeId);
            
            boolean success = feeService.deleteFee(feeId);
            
            if (success) {
                out.write("{\"success\":true,\"message\":\"费用删除成功\"}");
            } else {
                out.write("{\"success\":false,\"message\":\"费用删除失败\"}");
            }
        } catch (NumberFormatException e) {
            System.err.println("费用ID格式错误: " + e.getMessage());
            out.write("{\"success\":false,\"message\":\"无效的费用ID\"}");
        } catch (Exception e) {
            System.err.println("删除费用时发生错误: " + e.getMessage());
            e.printStackTrace();
            out.write("{\"success\":false,\"message\":\"删除费用失败：" + e.getMessage() + "\"}");
        }
    }
}
