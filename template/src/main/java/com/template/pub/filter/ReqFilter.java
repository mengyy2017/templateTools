package com.template.pub.filter;

import com.common.pub.consts.RespConsts;
import com.common.util.RespUtil;
import com.template.pub.consts.Consts;
import com.template.utils.ThreadLocalUtil;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (SecurityContextHolder.getContext().getAuthentication() == null && !Consts.LOGIN_CHEK_URL.equals(req.getRequestURI())){
            RespUtil.printFailResponse("未认证", RespConsts.CODE_UNAUTHORIZED, resp);
            return;
        }

        ThreadLocalUtil.getRequestThreadLocal().set(req);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
