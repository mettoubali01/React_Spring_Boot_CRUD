package com.example.crud_react_js.configuration;

import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
