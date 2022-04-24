package com.template.servlet.api;


import com.template.entity.User;
import com.template.entity.UserDTO;
import com.template.entity.resp.RestBean;
import com.template.service.UserService;
import com.template.service.impl.UserServiceImpl;
import com.template.utils.FastJsonUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "login",value = {"/login"})
public class LoginServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    /**
     *  验证登录
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userPortrait = Integer.parseInt(request.getParameter("userPortrait"));

        String usernameSession = (String) request.getSession().getAttribute("username");

        RestBean<Void> restBean;
        // 已登录直接返回成功 不再添加信息
        if (usernameSession != null && usernameSession.equals(username)) {
            restBean = new RestBean<>(200, "登录成功!");
        }else {
            User user = userService.findUserByUsernameAndPassword(username, password);
            if (user != null) {
                ServletContext context = this.getServletContext();
                List userList = (List) context.getAttribute("users");
                if (userList == null) {
                    userList = new ArrayList<>();
                    context.setAttribute("users", userList);
                }
                UserDTO userDTO = new UserDTO(user.getUsername(),user.getNickname(),userPortrait);
                userList.add(userDTO);


                // 保存用户信息...
                request.getSession().setAttribute("user", userDTO);

                restBean = new RestBean<>(200, "登录成功!");
            } else {
                restBean = new RestBean<>(304, "登录失败,账户或密码错误!");
            }
        }
        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);
    }
}
