package com.template.servlet.test;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/test/checkcode")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int width=100;
        int height = 30;
        //在内存中生成一张图片
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();//得到画笔
        graphics.setColor(Color.yellow);
        graphics.fillRect(0,0,width,height);//填充背景色
        //画边框
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0,0,width-1,height-1);
        //生成验证码
        String str="ABCDEFGHIGKLMNOPQRSTUVWXYZ123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int index =-1;
        //设置画笔字体和颜色
        graphics.setFont(new Font("Console", Font.BOLD, 18));
        graphics.setColor(Color.red);
        for(int i=1;i<=4;i++){
            index=random.nextInt(str.length());
            char ch = str.charAt(index);
            stringBuilder.append(ch);
            //将生成的随机字符画到图片上面
            graphics.drawString(ch+"",width/5*i,height/2+5);

        }
        String codes = stringBuilder.toString();
        //把验证码存储到session中
        req.getSession().setAttribute("checkCode",codes);
        //生成干扰线
        graphics.setColor(Color.green);
        for (int i = 0; i < 5; i++) {
            int x1=random.nextInt(width);
            int x2=random.nextInt(width);
            int y1=random.nextInt(height);
            int y2=random.nextInt(height);

            graphics.drawLine(x1,y1,x2,y2);
        }
        ImageIO.write(image,"jpg",resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
