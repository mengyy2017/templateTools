package com.template.pub.config.aop;

import com.template.pub.config.security.ResourceServiceConfig;
import com.template.pub.consts.Consts;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ConnAspect {

     // @Autowired
     // ResourceServiceConfig reSourceServiceConfig;

    // 这个拦截不到 没有加spring的注解  TableEntity上的那个注解是java的
    @Before("execution(* com.template.bussiness.entity.TableEntity.*(..))")
    public void beforeTableEntity(JoinPoint joinPoint){
        String bb = "";
    }

//    @Before("execution(* com.template.bussiness.controller.MenuController.*(..))")
//    public void beforeMenuController(JoinPoint joinPoint){
//        HttpServletRequest req = (HttpServletRequest) joinPoint.getArgs()[0];
//        req.setAttribute(Consts.AUTHTOKEN, Consts.SERCURITY_DATABASE_AUTHTOKEN);
//    }

    // @Before("execution(* org.springframework.web.client.RestTemplate.*(..))")
    // public void beforeTempalteExecute(JoinPoint joinPoint){
    //     Object[] params = joinPoint.getArgs();
    //     HttpEntity httpEntity = ((HttpEntity)params[2]);
    //     String accessToken = ((LinkedMultiValueMap) httpEntity.getBody()).get("access_token").get(0).toString();
    //     httpEntity.getHeaders().add("access_token", accessToken);
    //
    // }

//     @After("execution(* com.template.bussiness.controller.MenuController.updateOrSave(..))")
//     public void afterMenuController(JoinPoint joinPoint){
//         try {
//             reSourceServiceConfig.getAntMatchers();
//             reSourceServiceConfig.configure((HttpSecurity) joinPoint.getArgs()[0]);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     起作用了 得是spring管理的类 才可以拦截
//    @Before("execution(* com.template.service.TableService.*(..))")
//    public void aaaaa(JoinPoint joinPoint){
//        Object a = joinPoint.getTarget();
//        System.out.println("代理TableService的方法");
//    }

}
