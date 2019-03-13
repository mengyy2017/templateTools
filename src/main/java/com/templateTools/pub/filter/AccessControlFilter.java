package com.templateTools.pub.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class AccessControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest res = (HttpServletRequest)request;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Enumeration paramNames = request.getParameterNames();
        HttpServletResponse resp = (HttpServletResponse)response;
        String origin = res.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Headers", "content-type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(200);
        chain.doFilter(res, resp);
    }
}
