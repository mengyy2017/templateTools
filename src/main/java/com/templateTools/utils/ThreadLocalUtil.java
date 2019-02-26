package com.templateTools.utils;

import com.templateTools.entity.CreateInfo;

import javax.servlet.http.HttpServletRequest;

public class ThreadLocalUtil {

    private static ThreadLocal<CreateInfo> createInfoThreadLocal = new ThreadLocal();

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal();

    public static ThreadLocal<CreateInfo> getCreateInfoThreadLocal() {
        return createInfoThreadLocal;
    }

    public static void setCreateInfoThreadLocal(ThreadLocal<CreateInfo> createInfoThreadLocal) {
        ThreadLocalUtil.createInfoThreadLocal = createInfoThreadLocal;
    }

    public static ThreadLocal<HttpServletRequest> getRequestThreadLocal() {
        return requestThreadLocal;
    }

    public static void setRequestThreadLocal(ThreadLocal<HttpServletRequest> requestThreadLocal) {
        ThreadLocalUtil.requestThreadLocal = requestThreadLocal;
    }
}
