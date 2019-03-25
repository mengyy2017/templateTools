package com.templateTools.pub.filter;

import com.sun.deploy.net.HttpResponse;
import com.templateTools.pub.common.RespConsts;
import com.templateTools.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ReqFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (SecurityContextHolder.getContext().getAuthentication() == null){
            resp.setCharacterEncoding("utf-8");
            response.setContentType("text/plain");
            resp.setStatus(RespConsts.CODE_UNAUTHORIZED);
            resp.getWriter().print("未登陆！");
            return;
        }
        ThreadLocalUtil.getRequestThreadLocal().set(req);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
