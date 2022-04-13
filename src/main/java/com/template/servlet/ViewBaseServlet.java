package com.template.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  基础控制器
 */
public class ViewBaseServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        //初始化thymeleaf模板引擎
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setPrefix("/WEB-INF/templates/");  //设置前缀
        templateResolver.setSuffix(".html");                //设置后缀
        templateResolver.setCharacterEncoding("utf-8");     //设置编码

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * 处理模板
     * @param templateName  模板名字 不带后缀 如 index
     * @throws IOException
     */
    protected void process(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        WebContext webContext = new WebContext(request,response,this.getServletContext());
        templateEngine.process(templateName,webContext,response.getWriter());
    }
}
