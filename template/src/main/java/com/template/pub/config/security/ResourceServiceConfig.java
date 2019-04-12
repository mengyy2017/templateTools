package com.template.pub.config.security;

import com.common.pub.config.security.AuthoriTokenService;
import com.common.pub.config.security.ResourceAssistant;
import com.grpc.client.SystemGrpcClient;
import com.grpc.proto.menu.MenuMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SystemGrpcClient systemGrpcClient;

    public static Map<String, String> antMatcherMap;

    @PostConstruct
    public void getAntMatchers(){

        List<MenuMsg> menuMsgList = systemGrpcClient.getAllMenu(MenuMsg.newBuilder().setModule("template").build());

        antMatcherMap = ResourceAssistant.buildAntMatchMap(menuMsgList);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ResourceAssistant.buildHttpSecurity(antMatcherMap, http);
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
