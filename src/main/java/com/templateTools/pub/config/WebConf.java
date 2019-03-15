package com.templateTools.pub.config;

import com.templateTools.pub.interceptor.ReqInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebConf implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReqInterceptor()).addPathPatterns("/**");
    }

}
