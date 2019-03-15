package com.templateTools.pub.config.confModel;

import com.templateTools.entity.SysMenuEntity;
import com.templateTools.service.SysMenuService;
import com.templateTools.utils.BuildUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class MetadataSource implements FilterInvocationSecurityMetadataSource {

    public static Map<String, String> sysMenuPerMap;

    // 根据访问资源的地址查找所需要的权限
    // 如果返回的结果是null 说明访问这个路径不需要权限 就不会再走decide方法
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        Collection<ConfigAttribute> attrs;

        String permission = sysMenuPerMap.get(requestUrl);

        if (permission == null || permission.equals(""))
            attrs =  Arrays.asList(new SecurityConfig("NOPERMISSION"));
        else
            attrs =  Arrays.asList(new SecurityConfig(permission));

        return attrs;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Arrays.asList(new SecurityConfig("ROLE_ADMIN"));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
