package com.template.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

//@WebFilter(filterName = "iconFilter",value = {"/*"})
public class IconFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("URI:"+request.getRequestURI());
        System.out.println("URL:"+request.getRequestURL());
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("==========Head信息========");
        while ( headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            System.out.println(s+":"+request.getHeader(s));
        }
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
