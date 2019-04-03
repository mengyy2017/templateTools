package com.template.utils;

import com.template.entity.model.CreateInfo;
import com.template.pub.common.Consts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ThreadLocalUtil {

    private static ThreadLocal<CreateInfo> createInfoThreadLocal = new ThreadLocal();

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal();

    public static String getAuthToken() {

        HttpServletRequest req = ThreadLocalUtil.requestThreadLocal.get();

        if (req.getAttribute(Consts.AUTHTOKEN) == Consts.SERCURITY_DATABASE_AUTHTOKEN)
            return Consts.SERCURITY_DATABASE_AUTHTOKEN_STR;

        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (Consts.AUTHTOKEN.equals(cookie.getName()))
                    return cookie.getValue();
            }
        }

        return null;
    }

    public static ThreadLocal<CreateInfo> getCreateInfoThreadLocal() {
        return createInfoThreadLocal;
    }

    public static ThreadLocal<HttpServletRequest> getRequestThreadLocal() {
        return requestThreadLocal;
    }

}
