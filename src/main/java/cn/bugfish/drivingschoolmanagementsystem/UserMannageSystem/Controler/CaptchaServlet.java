package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CaptchaServlet")
public class CaptchaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        Random random = new Random();
        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));
            captcha.append(c);
            g.setColor(new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)));
            g.setFont(new Font("Arial", Font.BOLD, 20 + random.nextInt(10)));
            g.drawString(String.valueOf(c), 20 * i + 10, 30);
        }

        HttpSession session = request.getSession();
        session.setAttribute("captcha", captcha.toString());
        ImageIO.setUseCache(false);
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

}
