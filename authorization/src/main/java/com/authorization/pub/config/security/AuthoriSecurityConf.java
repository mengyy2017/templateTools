package com.authorization.pub.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class AuthoriSecurityConf extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private AuthoriDetailsService authoriDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authoriDetailsService).passwordEncoder(new RawEncoder());
        //不删除凭据，以便记住用户
//        auth.eraseCredentials(false);
    }

//    // 如果这个模块有controller需要访问的话 需要加上csrf关闭 和cors访问 没有就可以注销掉
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        http.cors().configurationSource(CorsConf.corsConfigurationSource());
//
//        http.authorizeRequests().antMatchers("/grpc/sayHello").permitAll();
//    }
//

}
