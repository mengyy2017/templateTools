package com.templateTools.utils;

import com.templateTools.entity.CreateInfo;

public class ThreadLocalUtil {

    private static ThreadLocal<CreateInfo> threadLocal = new ThreadLocal();

    public static ThreadLocal<CreateInfo> getThreadLocal() {
        return threadLocal;
    }

    public static void setThreadLocal(ThreadLocal<CreateInfo> threadLocal) {
        ThreadLocalUtil.threadLocal = threadLocal;
    }
}
