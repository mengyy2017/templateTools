package com.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com", exclude = { SecurityAutoConfiguration.class })
//@ComponentScan(basePackages = "com.spider")  // 这个可以注销掉 默认就是扫描这个目录
@MapperScan(basePackages = "com.spider.bussiness.dao")
public class SpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class);
    }

//    @Bean
//    public FilterRegistrationBean accessControlFilterFactory(){
//        FilterRegistrationBean accessControlFilter = new FilterRegistrationBean(new AccessControlFilter());
//        accessControlFilter.addUrlPatterns("/*");
//        accessControlFilter.setName("accessControlFilter");
//        return accessControlFilter;
//    }
}
