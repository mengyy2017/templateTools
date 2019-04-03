package com.authorization;

import com.authorization.pub.config.web.filter.ReqFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.templateTools")  // 这个可以注销掉 默认就是扫描这个目录
@MapperScan(basePackages = "com.authorization.bussiness.dao")
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class);
    }

    @Bean
    public FilterRegistrationBean accessControlFilterFactory(){
        FilterRegistrationBean reqFilter = new FilterRegistrationBean(new ReqFilter());
        reqFilter.addUrlPatterns("/*");
        reqFilter.setName("reqFilter");
        return reqFilter;
    }

}
