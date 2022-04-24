package com.template.servlet.api;

import com.template.entity.resp.RestBean;
import com.template.utils.FastJsonUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "sysMsg",value = {"/systemMessage"})
public class SysMessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = this.getServletContext();

        Integer usersNum = (Integer) context.getAttribute("usersNum");
        if (usersNum == null) usersNum = 0;

        HttpSession session = request.getSession();
        Integer curUsersNum = (Integer) session.getAttribute("curUsersNum");
        if (curUsersNum == null) curUsersNum = 0;



        RestBean<List> restBean;
        if (usersNum > curUsersNum) {
            List userList = (List) context.getAttribute("users");
            List list = userList.subList(curUsersNum, usersNum);
            session.setAttribute("curUsersNum", usersNum);
            restBean = new RestBean<>(200, "消息接收成功!", list);
        } else {
            restBean = new RestBean<>(204, "没有新消息!");
        }


        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);

    }
}
