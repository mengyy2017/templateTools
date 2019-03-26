package com.templateTools.pub.config;

import com.templateTools.pub.common.Consts;
import com.templateTools.pub.config.confModel.*;
import com.templateTools.pub.filter.ReqFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {


    private static String indexUrl = "/account/index";
    private static String logoutUrl = "/account/logout";
    private static String accDeniedUrl = "/account/accDenied";

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 这个设置不拦截静态资源
        web.ignoring().antMatchers(Consts.loginUrl); // 处理发送登录信息的地址在http那的loginProcessingUrl()方法
                                            // 这个是没有登录时报错时要访问的地址 需要放行 这个地址还没有走到默认的拦截就被自定义的给拦截到了 放行这里起不了作用
//        web.ignoring().antMatchers(accDeniedUrl);
    }

    @Autowired
    private ReqFilter reqFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(CorsConfigurationSource());

        http.formLogin().loginProcessingUrl(Consts.LOGIN_CHEK_URL)
                .loginPage(Consts.loginUrl).usernameParameter("username").passwordParameter("password")
                .successForwardUrl(indexUrl).failureHandler((request, response, exception) -> {
                    exception.printStackTrace();
                });

        http.logout().logoutUrl(logoutUrl).logoutSuccessUrl(logoutUrl).invalidateHttpSession(false);

        http.csrf().disable();

        http.sessionManagement().sessionAuthenticationFailureHandler((request, response, exception) -> {
            exception.printStackTrace();
        });

        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            accessDeniedException.printStackTrace();
        });

        http.addFilterBefore(reqFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filter(), FilterSecurityInterceptor.class);
    }

    //配置跨域访问资源
    private CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
        corsConfiguration.setAllowCredentials(true);
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义UserDetailsService,设置加密算法
        auth.userDetailsService(accountDetailsService).passwordEncoder(new RawEncoder());
//                .passwordEncoder(passwordEncoder())
        //不删除凭据，以便记住用户
//        auth.eraseCredentials(false);
    }

    @Autowired
    MetadataSource metadataSource;

    @Autowired
    AccDeciManager accDeciManager;

    @Bean
    public FilterSecurityInterceptor filter() throws Exception {
        FilterSecurityInterceptor filter = new FilterSecurityInterceptor();
        filter.setSecurityMetadataSource(metadataSource);
        filter.setAccessDecisionManager(accDeciManager);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}
