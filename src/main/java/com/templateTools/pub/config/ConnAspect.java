package com.templateTools.pub.config;

import com.templateTools.pub.common.Consts;
import com.templateTools.utils.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ConnAspect {

    @Before("execution(* java.sql.Connection.*())")
    public void beforeConnectionClose(JoinPoint joinPoint) {
        String authToken = ThreadLocalUtil.getAuthToken();
//        ConcurrentHashMap<String, LinkedList<Connection>> conHM = CreateConnUtil.getDataSourceConHM();
        Object a = joinPoint.getTarget();
        String aa = "";
    }

    // 这个拦截不到 没有加spring的注解  TableEntity上的那个注解是java的
    @Before("execution(* com.templateTools.entity.TableEntity.*(..))")
    public void beforeTableEntity(JoinPoint joinPoint){
        String bb = "";
    }

    @Before("execution(* com.templateTools.controller.MenuController.*(..))")
    public void beforeMenuController(JoinPoint joinPoint){
//        joinPoint.get
        HttpServletRequest req = (HttpServletRequest) joinPoint.getArgs()[0];
        req.setAttribute(Consts.AUTHTOKEN, Consts.SERCURITY_DATABASE_AUTHTOKEN);
    }

//     起作用了 得是spring管理的类 才可以拦截
//    @Before("execution(* com.templateTools.service.TableService.*(..))")
//    public void aaaaa(JoinPoint joinPoint){
//        Object a = joinPoint.getTarget();
//        System.out.println("代理TableService的方法");
//    }

}
