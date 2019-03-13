package com.templateTools.pub.config;

import com.templateTools.pub.common.Consts;
import com.templateTools.pub.config.confModel.AccDeciManager;
import com.templateTools.pub.config.confModel.AccountDetailsService;
import com.templateTools.pub.config.confModel.MetadataSource;
import com.templateTools.pub.config.confModel.RawEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    private static String loginUrl = "/account/login";
    private static String indexUrl = "/account/index";
    private static String logoutUrl = "/account/logout";
    private static String accDeniedUrl = "/account/accDenied";

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 这个设置不拦截静态资源
//        web.ignoring().antMatchers(loginUrl); // 登录在http那设置
//        web.ignoring().antMatchers(accDeniedUrl);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(CorsConfigurationSource());

        http.formLogin().loginProcessingUrl(Consts.LOGIN_CHEK_URL)
                .usernameParameter("username").passwordParameter("password")
                .loginPage(loginUrl).successForwardUrl(indexUrl)
                .failureHandler((request, response, exception) -> {
                    exception.printStackTrace();
                });
        http.logout().logoutUrl(logoutUrl).logoutSuccessUrl(logoutUrl).invalidateHttpSession(true);

        http.csrf().disable();
//        http.csrf().ignoringRequestMatchers(getRequestMatcher());

        http.sessionManagement().sessionAuthenticationFailureHandler((request, response, exception) -> {
            exception.printStackTrace();
        });

        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            accessDeniedException.printStackTrace();
        });

        http.addFilterBefore(interceptor(), FilterSecurityInterceptor.class);
    }

    @Bean
    public RequestMatcher getRequestMatcher(){
        Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
        RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher("/unprotected", null);

        RequestMatcher requestMatcher = new RequestMatcher() {
            @Override
            public boolean matches(HttpServletRequest request) {
                if(allowedMethods.matcher(request.getMethod()).matches()){
                    return false;
                }

                return !unprotectedMatcher.matches(request);
            }
        };
        return requestMatcher;
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
//        auth.
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
    public FilterSecurityInterceptor interceptor() throws Exception {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setSecurityMetadataSource(metadataSource);
        interceptor.setAccessDecisionManager(accDeciManager);
        interceptor.setAuthenticationManager(authenticationManagerBean());
        return interceptor;
    }

}
