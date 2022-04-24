package com.template.servlet.api;

import com.template.entity.UserDTO;
import com.template.entity.resp.RestBean;
import com.template.utils.FastJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "users",urlPatterns = {"/user-nickName","/user-onlineList"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.endsWith("/user-nickName")){
            getNickName(request,response);
        }else if(uri.endsWith("/user-onlineList")){
            getOnlineList(request,response);
        }

    }

    private void getNickName(HttpServletRequest request,HttpServletResponse response) throws IOException {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
        RestBean<String > restBean;
        if(userDTO != null){
            restBean = new RestBean<>(200,"获取用户昵称成功", userDTO.getNickname());
        }else {
            restBean = new RestBean<>(401,"请先登录");
        }

        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);

    }

    private void getOnlineList(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List users = (List) this.getServletContext().getAttribute("users");
        RestBean<List > restBean;
        if(users != null){
            restBean = new RestBean<>(200,"获取用户在线列表成功",users);
        }else {
            restBean = new RestBean<>(401,"获取失败");
        }
        String res = FastJsonUtils.toJSONString(restBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(res);
    }
}
