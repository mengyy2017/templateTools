package com.authorization.pub.config.security;

import com.authorization.pub.consts.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    private static String indexUrl = "/account/index";
    private static String logoutUrl = "/account/logout";
    private static String accDeniedUrl = "/account/accDenied";

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl(Consts.LOGIN_CHEK_URL)
                .loginPage(Consts.loginUrl).usernameParameter("username").passwordParameter("password").permitAll()
                .successForwardUrl(indexUrl).failureHandler((request, response, exception) -> {
                        exception.printStackTrace();
                    })
                .and().authorizeRequests()//定义哪些url需要被保护  哪些不需要保护
                .antMatchers("/oauth/token" , "/oauth/check_token", "/oauth/authorize").permitAll()//定义这两个链接不需要登录可访问
                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
                ;

        http.logout().logoutUrl(logoutUrl).logoutSuccessUrl(logoutUrl).invalidateHttpSession(false);

        http.csrf().disable();

        http.sessionManagement().sessionAuthenticationFailureHandler((request, response, exception) -> {
            exception.printStackTrace();
        });

        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            accessDeniedException.printStackTrace();
        });
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(new RawEncoder());
        //不删除凭据，以便记住用户
//        auth.eraseCredentials(false);
    }

}
