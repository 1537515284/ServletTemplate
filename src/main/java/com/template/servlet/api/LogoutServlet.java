package com.template.servlet.api;

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
import java.util.List;

@WebServlet(name = "logout",value = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RestBean<String > restBean;
        if (session == null) {
            restBean = new RestBean<>(401,"请先登录!");
        }else {
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            session.invalidate();
            deleteFromUserList(request, userDTO.getUsername());
            restBean = new RestBean<>(200,"退出登录成功");
        }

        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);

    }

    private void deleteFromUserList(HttpServletRequest request,String username){
        ServletContext context = this.getServletContext();
        List userList = (List) context.getAttribute("users");

        for (int i = 0; i < userList.size(); i++) {
            UserDTO userDTO = (UserDTO) userList.get(i);
            if(userDTO.getUsername().equals(username)) {
                userList.remove(i);
                context.setAttribute("usersNum",userList.size());
                break;
            }
        }
    }
}
