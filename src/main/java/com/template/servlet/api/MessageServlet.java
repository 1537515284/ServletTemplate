package com.template.servlet.api;

import com.template.entity.Message;
import com.template.entity.MessageDTO;
import com.template.entity.User;
import com.template.entity.UserDTO;
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
import java.util.*;

@WebServlet(name = "message",value = {"/message"})
public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = this.getServletContext();
        HttpSession session = request.getSession();

        Integer oldListLength = (Integer) session.getAttribute("oldListLength");

        Integer listLength = (Integer) context.getAttribute("listLength");

        if (oldListLength == null) {
            oldListLength = 0;
            session.setAttribute("oldListLength",oldListLength);
        }
        if (listLength == null) listLength = 0;

        RestBean<List> restBean;
        if (listLength > oldListLength) {
            List messageList = (List) context.getAttribute("messageList");
            List list = messageList.subList((int) oldListLength, (int) listLength);
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            List filterList = filterOwnMsg(list, userDTO.getUsername());
            session.setAttribute("oldListLength",listLength);

            if(filterList.isEmpty())
                restBean = new RestBean<>(204, "没有新消息!");
            else
                restBean = new RestBean<>(200, "消息接收成功!", list);
        } else {
            restBean = new RestBean<>(204, "没有新消息!");
        }

        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);
    }

    /**
     * 过滤消息列表中 自己发的消息
     * @param list
     * @param username
     * @return
     */
    private List filterOwnMsg(List list,String username){
        List res = new ArrayList();
        for (Object o : list) {
            MessageDTO msg = (MessageDTO) o;
            if(msg.getSender_username().equals(username))
                continue;
            res.add(o);
        }
        return res;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");


        String content = request.getParameter("message");
        String time = request.getParameter("time");
        MessageDTO message = new MessageDTO(userDTO.getUsername(), userDTO.getNickname(), userDTO.getUserPortrait(),content,time);
        ServletContext context = this.getServletContext();
        List messageList = (List)context.getAttribute("messageList");
        if (messageList == null) {
            messageList = new ArrayList();
            context.setAttribute("messageList",messageList);
        }
        messageList.add(message);
        context.setAttribute("listLength",messageList.size());



        RestBean<String  > restBean = new RestBean<>(200,"消息发送成功!");

        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);
    }
}
