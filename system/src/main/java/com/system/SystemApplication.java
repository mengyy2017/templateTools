package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.templateTools")  // 这个可以注销掉 默认就是扫描这个目录
@MapperScan(basePackages = "com.system.bussiness.dao")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class);
    }

//    @Bean
//    public FilterRegistrationBean accessControlFilterFactory(){
//        FilterRegistrationBean reqFilter = new FilterRegistrationBean(new ReqFilter());
//        reqFilter.addUrlPatterns("/*");
//        reqFilter.setName("reqFilter");
//        return reqFilter;
//    }

}
