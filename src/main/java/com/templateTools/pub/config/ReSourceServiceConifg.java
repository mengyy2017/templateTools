package com.templateTools.pub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ReSourceServiceConifg extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("createCode").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().requestMatchers().anyRequest()
                .and().anonymous().and().authorizeRequests()
                .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
                .antMatchers("/database/**").authenticated()
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

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**" , corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
