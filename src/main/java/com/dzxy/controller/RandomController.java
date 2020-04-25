package com.dzxy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class RandomController{
    public static Color getRandColor(int min, int max) {
        Random r = new Random();
        if (min > 255)
            min = 255;
        if (max > 255)
            max = 255;
        int red = r.nextInt(max - min) + min;
        int green = r.nextInt(max - min) + min;
        int blue = r.nextInt(max - min) + min;
        return new Color(red, green, blue);
    }

    @RequestMapping("/Rand.png")
    public void random(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //在内存中创建图像
        int width = 110, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        //获取图形上下文
        Graphics g = image.getGraphics();
        //生成随机类
        Random random = new Random();
        //设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        //设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //随机产生155条干扰线，是图像中的验证码不易别其他程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        //取随机产生的认证码（6位数字）
        String sRand = "";
        for (int i = 0; i < 6; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            //将认证码显示到图像中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 13 * i + 6, 16);
        }
        //图像生效
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", resp.getOutputStream());
        } catch (Exception e) {
            System.out.println("验证码图片产生出现错误：" + e.toString());
        }
        //保存验证码到session中
        HttpSession session = req.getSession();
        session.setAttribute("randStr", sRand);
    }
}
