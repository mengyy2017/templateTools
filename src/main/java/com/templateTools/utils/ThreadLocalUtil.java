package com.templateTools.utils;

import com.templateTools.entity.model.CreateInfo;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ThreadLocalUtil {

    private static ThreadLocal<CreateInfo> createInfoThreadLocal = new ThreadLocal();

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal();

    public static ThreadLocal<CreateInfo> getCreateInfoThreadLocal() {
        return createInfoThreadLocal;
    }

    public static ThreadLocal<HttpServletRequest> getRequestThreadLocal() {
        return requestThreadLocal;
    }

    public static String getAuthToken() {

        Cookie[] cookies = ThreadLocalUtil.requestThreadLocal.get().getCookies();

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName()))
                    return cookie.getValue();
            }
        }

        return null;
//        return ThreadLocalUtil.requestThreadLocal.get().getHeader("authToken");
    }

}
