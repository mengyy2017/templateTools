package com.templateTools.pub.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Collections;

@Configuration
@EnableResourceServer
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class ReSourceServiceConifg extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().requestMatchers().anyRequest()
                .and().anonymous().and().authorizeRequests()
                .antMatchers("/product/**").permitAll()
                .antMatchers("/database/**").access("#oauth2.hasScope('select') and hasRole('ROLE_ADMIN')")
                .antMatchers("/menu/**").authenticated()
                .and().cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedOrigin(request.getHeader("Origin"));
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setMaxAge(3600L);
            return corsConfiguration;
        });
    }

//    @Bean
//    public ResourceServerTokenServices tokenServices() {
//        RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setTokenName("access_token");
//        tokenService.setCheckTokenEndpointUrl("http://localhost:8060/oauth/check_token");
//        tokenService.setClientId("createCodeClient");
//        tokenService.setClientSecret("secret");
////        tokenService.
////        RestOperations restOperations = new OAuth2RestTemplate();
////        tokenService.setAccessTokenConverter(accessTokenConverter());
//        return tokenService;
//    }

//    @Bean
//    public static RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("User-Agent", "curl/7.58.0");
//        headers.add("Authorization ", "");
//        HttpEntity<String> entity = new HttpEntity<>("", headers);
//
//        ResponseEntity<JSONObject> response = restTemplate.postForEntity("http://localhost:8060/oauth/check_token", entity, JSONObject.class);
//        return restTemplate;
//    }

//    @Bean
//    public AccessTokenConverter accessTokenConverter() {
//        return new DefaultAccessTokenConverter();
//    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("createCode").stateless(true);
    }

}
