package com.system.pub.config.security;

import com.common.pub.config.security.AuthoriTokenService;
import com.common.util.BuildUtil;
import com.common.pub.config.security.ResourceAssistant;
import com.system.bussiness.entity.MenuEntity;
import com.system.bussiness.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private MenuService menuService;

    public static Map<String, String> antMatcherMap;

    @PostConstruct
    public void getAntMatchers(){

        List<MenuEntity> menuEntityList = menuService.select(BuildUtil.newAndSet0(MenuEntity::new, new String[]{"system"}, MenuEntity::setModule));

        antMatcherMap = ResourceAssistant.buildAntMatchMap(menuEntityList);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/account/login").permitAll();

        ResourceAssistant.buildHttpSecurity(antMatcherMap, http);

    }

   @Bean
   @Primary
   public ResourceServerTokenServices tokenServices() {
       return new AuthoriTokenService();
   }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("createCode").stateless(true);
//    }

}
