package com.authorization.pub.config.security;

import com.authorization.bussiness.entity.RoleEntity;
import com.authorization.bussiness.entity.UserEntity;
import com.authorization.bussiness.entity.model.UserSecurity;
import com.authorization.bussiness.service.UserService;
import com.common.util.BuildUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class AuthoriDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userService.selectUserAndRole(BuildUtil.newAndSet(UserEntity::new, BuildUtil.getVAndF(username, UserEntity::setUsername)));

        UserSecurity userSecurity = new UserSecurity(userEntity.getUsername(), userEntity.getPassword()
                                        , userEntity.getRoleEntityList().stream().map(RoleEntity::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return userSecurity;
    }



}
