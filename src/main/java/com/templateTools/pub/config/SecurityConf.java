package com.templateTools.pub.config;

import com.templateTools.pub.config.confModel.AccDeciManager;
import com.templateTools.pub.config.confModel.MetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 这个设置不拦截静态资源
//        web.ignoring().antMatchers("/login"); // 登录在http那设置
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义UserDetailsService,设置加密算法
//        auth.userDetailsService()
//                .passwordEncoder(passwordEncoder())
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    @Autowired
    MetadataSource metadataSource;

    @Autowired
    AccDeciManager accDeciManager;

    @Bean
    public FilterSecurityInterceptor interceptor() throws Exception {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setSecurityMetadataSource(metadataSource);
        interceptor.setAccessDecisionManager(accDeciManager);
        interceptor.setAuthenticationManager(authenticationManagerBean());
        return interceptor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").successForwardUrl("/index").failureForwardUrl("/login");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/logout").invalidateHttpSession(true);
        http.exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout").and()
                .exceptionHandling().accessDeniedPage("/noPermission");
        http.sessionManagement().invalidSessionUrl("/login");
        http.addFilterBefore(interceptor(), FilterSecurityInterceptor.class);
    }

}
