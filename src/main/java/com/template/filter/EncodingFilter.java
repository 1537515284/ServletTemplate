package com.template.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * 编码过滤器
 */
@WebFilter(filterName = "encodingFilter",value = {"/*"})
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");   // 设置从request中取得的值的编码格式 (解决post中文参数乱码)
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
