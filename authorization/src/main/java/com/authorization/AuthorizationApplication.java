package com.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.templateTools")  // 这个可以注销掉 默认就是扫描这个目录
@MapperScan(basePackages = "com.authorization.dao")
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class);
    }

//    @Bean
//    public FilterRegistrationBean accessControlFilterFactory(){
//        FilterRegistrationBean accessControlFilter = new FilterRegistrationBean(new AccessControlFilter());
//        accessControlFilter.addUrlPatterns("/*");
//        accessControlFilter.setName("accessControlFilter");
//        return accessControlFilter;
//    }
}