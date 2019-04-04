package com.template.pub.config.security;

import com.common.util.BuildUtil;
import com.template.bussiness.entity.MenuEntity;
import com.template.bussiness.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ReSourceServiceConifg extends ResourceServerConfigurerAdapter {

    @Autowired
    private MenuService menuService;

    public static Map<String, String> antMatcherMap;

    @PostConstruct
    public void getAntMatchers(){
        List<MenuEntity> sysMenuList = menuService.selectAll();

        LinkedList<String> linkedList = sysMenuList.parallelStream().collect(
                LinkedList::new, (l, e) -> { l.add(e.getUrl()); l.add(e.getPermission()); }, (l1, l2) -> l1.addAll(l2)
        );

        antMatcherMap = BuildUtil.putsValsLoop(new HashMap<>(), HashMap<String, String>::put, linkedList);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.cors().configurationSource(corsConfigurationSource());

        antMatcherMap.forEach((key, value) -> {
            try {
                http.authorizeRequests().antMatchers(key).hasAuthority(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        http.authorizeRequests().antMatchers("/**").authenticated();

//        http.formLogin().loginProcessingUrl(Consts.LOGIN_CHEK_URL)
//                .loginPage(Consts.loginUrl).usernameParameter("username").passwordParameter("password")
//                .successForwardUrl("/account/index").failureHandler((request, response, exception) -> {
//                        RespUtil.printFailResponse(exception.getMessage(), RespConsts.CODE_UNAUTHORIZED, response);
//                        exception.printStackTrace();
//                    });

//        http.exceptionHandling().authenticationEntryPoint((request, response, authException) ->
//                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

//        http.addFilterBefore(authoriSecur(), FilterSecurityInterceptor.class);

    }

    //配置跨域访问资源
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
        corsConfiguration.setAllowCredentials(true);
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }

   @Bean
   @Primary
   public ResourceServerTokenServices tokenServices() {
       return new AuthoriTokenService();
   }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("createCode").stateless(true);
    }

}
