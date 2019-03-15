package com.templateTools.pub.filter;

import com.templateTools.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ReqFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ThreadLocalUtil.getRequestThreadLocal().set((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
