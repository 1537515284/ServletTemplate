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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "sysMsg",value = {"/systemMessage"})
public class SysMessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = this.getServletContext();

        HttpSession session = request.getSession();


        List userList = (List) context.getAttribute("users");
        List usersSession = (List) session.getAttribute("usersSession");
        if(usersSession == null){
            usersSession = new ArrayList(userList);
            session.setAttribute("usersSession",usersSession);
        }

        int usersNum = userList.size();
        int curUsersNum = usersSession.size();

//        System.out.println(userList == usersSession);
//
//        System.out.println("userList = "+userList);
//        System.out.println("userSession = "+usersSession);
//
//        System.out.println("userNum = "+usersNum);
//        System.out.println("curUsersNum = "+curUsersNum);

        RestBean<List> restBean;
        // 在线用户人数发生变化
        if (usersNum > curUsersNum) {
            List finalUsersSession = usersSession;
            List reduce = (List) userList.stream().filter(item -> !finalUsersSession.contains(item)).collect(Collectors.toList());
            usersSession.addAll(reduce);
            restBean = new RestBean<>(200, "消息接收成功!", reduce);
        }else if(usersNum < curUsersNum){
            List reduce = (List) usersSession.stream().filter(item -> !userList.contains(item)).collect(Collectors.toList());
            usersSession.removeAll(reduce);
            restBean = new RestBean<>(201, "消息接收成功!", reduce);
        } else {
            restBean = new RestBean<>(204, "没有新消息!");
        }


        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);

    }
}
