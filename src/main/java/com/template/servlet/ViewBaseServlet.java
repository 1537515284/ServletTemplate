package com.template.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  基础控制器
 *  初始化thymeleaf模板引擎
 */
public class ViewBaseServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {

        // 1.获取ServletContext对象
        ServletContext servletContext = this.getServletContext();

        // 2.创建Thymeleaf解析对象
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        // 3.给解析器对象设置参数
        //  1.)HTML是默认模式，明确设置是为了代码更容易理解
        templateResolver.setTemplateMode(TemplateMode.HTML);

        //  2.)设置前缀
        templateResolver.setPrefix("/WEB-INF/templates/");

        //  3.)设置后缀
        templateResolver.setSuffix(".html");

        //  4.)设置缓存过期时间（毫秒）
        templateResolver.setCacheTTLMs(60000L);

        //  5.)设置是否开启缓存
        templateResolver.setCacheable(false);

        //  6.)设置服务器端编码方式
        templateResolver.setCharacterEncoding("utf-8");

        // 4.创建模板引擎对象
        templateEngine = new TemplateEngine();

        // 5.给模板引擎对象设置模板解析器
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
