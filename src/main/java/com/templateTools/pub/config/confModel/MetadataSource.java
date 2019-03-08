package com.templateTools.pub.config.confModel;

import com.templateTools.pub.common.Consts;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collection;

@Component
public class MetadataSource implements FilterInvocationSecurityMetadataSource {

    // 根据访问资源的地址查找所需要的权限
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        Collection<ConfigAttribute> attrs = null;
        if ("/database/setCreateInfo".matches(requestUrl)) {
            attrs = Arrays.asList(new SecurityConfig("ROLE_ADMIN"));
        } else if (requestUrl.matches("/account.*")){
            attrs = Arrays.asList(new SecurityConfig("ROLE_ANONYMOUS"));
        } else if (Consts.LOGIN_CHEK_URL.equals(requestUrl)){
            attrs = Arrays.asList(new SecurityConfig("ROLE_ANONYMOUS"));
        }

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
