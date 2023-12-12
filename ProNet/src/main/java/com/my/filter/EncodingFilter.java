package com.my.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EncodingFilter#init");
        encoding = filterConfig.getInitParameter("encoding");
        System.out.println("encoding ==> " + encoding);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("EncodingFilter#doFilter before");
        HttpServletRequest httpReq = (HttpServletRequest) request;
        System.out.println("EncodingFilter# " + httpReq.getPathInfo());
        System.out.println("EncodingFilter# " + httpReq.getRequestURI());
        System.out.println("EncodingFilter# " + httpReq.getRequestURL());
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(encoding);


        chain.doFilter(request, response);
        System.out.println("EncodingFilter#doFilter after");
    }
}
