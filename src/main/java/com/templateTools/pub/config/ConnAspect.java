package com.templateTools.pub.config;

import com.templateTools.utils.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class ConnAspect {

    @Before("execution(* java.sql.Connection.*())")
    public void beforConnectionClose(JoinPoint joinPoint) {
        String authToken = ThreadLocalUtil.getAuthToken();
        ConcurrentHashMap<String, LinkedList<Connection>> conHM = CreateCodeDataSource.getDataSourceConHM();
        Object a = joinPoint.getTarget();
        String aa = "";
    }

    @Before("execution(* com.templateTools.entity.CreateInfo.*(..))")
    public void aaa(){
        String bb = "";
    }

    @Before("execution(* com.templateTools.entity.TableEntity.*(..))")
    public void aaaa(){
        String bb = "";
    }

//     起作用了 得是spring管理的类 才可以拦截
//    @Before("execution(* com.templateTools.service.TableService.*(..))")
//    public void aaaaa(JoinPoint joinPoint){
//        Object a = joinPoint.getTarget();
//        String bb = "";
//    }


}
