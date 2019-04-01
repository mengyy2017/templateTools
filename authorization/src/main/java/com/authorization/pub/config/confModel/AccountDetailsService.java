package com.authorization.pub.config.confModel;

import com.authorization.entity.RoleEntity;
import com.authorization.entity.UserEntity;
import com.authorization.entity.model.UserSecurity;
import com.authorization.service.UserService;
import com.authorization.utils.BuildUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    // 只有访问设置的loginProcessingUrl那个地址 才会调用这个方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userService.selectUserAndRole(BuildUtil.newAndSet(UserEntity::new, BuildUtil.getVAndF(username, UserEntity::setUsername)));
//        UserEntity userEntity = userService.selectOne(newAndSet(UserEntity::new, getVAndF(username, UserEntity::setUsername)));

        UserSecurity userSecurity = new UserSecurity(userEntity.getUsername(), userEntity.getPassword()
                                        , userEntity.getRoleEntityList().stream().map(RoleEntity::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return userSecurity;
    }



}
