package com.templateTools.pub.config.confModel;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccDeciManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 所需要的
        List confAttrsList = configAttributes.stream().map(configAttribute -> configAttribute.getAttribute()).collect(Collectors.toList());
        // 有的
        List authList = authentication.getAuthorities().stream().map(auth -> ((GrantedAuthority) auth).getAuthority()).collect(Collectors.toList());
        confAttrsList.retainAll(authList);
        if (confAttrsList.size() > 0)
            return;
        else
            throw new AccessDeniedException("没有权限访问！");
//            throw new InsufficientAuthenticationException("没有权限访问！");
//            return;

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
