package com.templateTools;

import com.templateTools.base.mapper.CommonMapper;
import com.templateTools.pub.filter.AccessControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.templateTools")
@MapperScan(basePackages = "com.templateTools.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public FilterRegistrationBean accessControlFilterFactory(){
        FilterRegistrationBean accessControlFilter = new FilterRegistrationBean(new AccessControlFilter());
        accessControlFilter.addUrlPatterns("/*");
        accessControlFilter.setName("accessControlFilter");
        return accessControlFilter;

    }
}
