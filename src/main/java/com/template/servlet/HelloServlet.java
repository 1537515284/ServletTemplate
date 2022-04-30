package com.template.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Hello",value = {"/hello"})
public class HelloServlet extends ViewBaseServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("sourcing","https://github.com/1537515284/ServletTemplate.git");
        request.setAttribute("thymeleafHelp","https://blog.csdn.net/weixin_45636641/article/details/108249715");
        request.setAttribute("webHelp","https://www.jc2182.com/");
        process("hello",request,response);
    }
}
