package com.authorization.pub.config;

import com.authorization.pub.config.confModel.RawEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(new InMemoryTokenStore());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // .withClient("createCodeClient")
                // .resourceIds("createCode")
                // .authorizedGrantTypes("client_credentials", "refresh_token")
                // .scopes("select")
                // .authorities("client")
                // .secret("123456")
                // .and()
                .withClient("createCode")
                .resourceIds("createCode")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("select")
                .authorities("ROLE_ADMIN")
                .secret(new RawEncoder().encode("secret"));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        CorsConfigurationSource source = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
             CorsConfiguration corsConfiguration = new CorsConfiguration();
             corsConfiguration.addAllowedHeader("*");
             corsConfiguration.addAllowedOrigin(request.getHeader(HttpHeaders.ORIGIN));
             corsConfiguration.addAllowedMethod("*");
             corsConfiguration.setAllowCredentials(true);
             corsConfiguration.setMaxAge(3600L);
             return corsConfiguration;
            }
        };

        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients().addTokenEndpointAuthenticationFilter(new CorsFilter(source));
    }

}
