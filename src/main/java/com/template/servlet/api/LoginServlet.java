package com.template.servlet.api;

import com.template.pojo.User;
import com.template.service.UserService;
import com.template.service.impl.UserServiceImpl;
import com.template.utils.FastJsonUtils;
import com.template.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login",value = {"/api/login"})
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result res = login(request);
        FastJsonUtils.writeJson(res,response);
    }

    private Result login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");

        HttpSession session = request.getSession();
        String  correctCaptcha = (String) session.getAttribute("verificationCode");
        if ("".equals(correctCaptcha) || null == correctCaptcha) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!correctCaptcha.equalsIgnoreCase(captcha)) {
            return Result.fail().message("验证码有误,请重新输入");
        }

        // 从session域中移除现有验证码
        session.removeAttribute("correctCaptcha");
        try {
            User user = userService.login(username,password);
            if(null != user)
                return Result.ok();
            else
                throw new RuntimeException("用户名或者密码有误");
        } catch (RuntimeException e) {
            return Result.fail().message(e.getMessage());
        }
    }
}
