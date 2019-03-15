package com.templateTools;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.templateTools")  // 这个可以注销掉 默认就是扫描这个目录
@MapperScan(basePackages = "com.templateTools.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Bean
//    public FilterRegistrationBean accessControlFilterFactory(){
//        FilterRegistrationBean accessControlFilter = new FilterRegistrationBean(new AccessControlFilter());
//        accessControlFilter.addUrlPatterns("/*");
//        accessControlFilter.setName("accessControlFilter");
//        return accessControlFilter;
//    }
}
