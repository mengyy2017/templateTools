package com.templateTools.pub.config.confModel;

import com.templateTools.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class AccountDetailsService implements UserDetailsService {

    // 只有访问设置的loginProcessingUrl那个地址 才会调用这个方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = new UserEntity("admin", "123456",  Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return userEntity;
    }
}
