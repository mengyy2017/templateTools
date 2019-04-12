package com.common.pub.config.security;

import com.common.pub.config.cors.CorsConf;
import com.common.pub.pubBo.MenuFunInter;
import com.common.util.BuildUtil;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResourceAssistant {

    public static Map<String, String> buildAntMatchMap(List<? extends MenuFunInter> menuList){
        LinkedList<String> linkedList = menuList.parallelStream().collect(
                LinkedList::new, (l, e) -> { l.add(e.getUrl()); l.add(e.getPermission()); }, (l1, l2) -> l1.addAll(l2)
        );
        return BuildUtil.putsValsLoop(new HashMap<>(), HashMap<String, String>::put, linkedList);
    }


    public static void buildHttpSecurity(Map<String, String> antMatcherMap, HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.cors().configurationSource(CorsConf.corsConfigurationSource());

        antMatcherMap.forEach((key, value) -> {
            try {
                http.authorizeRequests().antMatchers(key).hasAuthority(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        http.authorizeRequests().antMatchers("/**").authenticated();
    }

}
