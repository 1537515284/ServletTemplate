package com.template.servlet.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/test/checkLogin")
public class CheckLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        String checkcode = req.getParameter("checkcode");

        //从session中取出正确的验证码
        HttpSession session = req.getSession();
        String  checkCode = (String) session.getAttribute("checkCode");
        if(checkCode!=null && !checkCode.equals("") && checkcode.equalsIgnoreCase(checkCode))
        {
            //判断用户名密码是否正确
            if(username.equals("admin") && pwd.equals("123")){
                //登录成功,把用户信息放到session中
                //req.getSession().setAttribute("user",obj);
                resp.sendRedirect("/index.jsp");
            }else{
                //用户名或密码错误
                req.setAttribute("msg","用户名或密码错误");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }else{
            //验证码输入错误
            req.setAttribute("msg","验证码错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}