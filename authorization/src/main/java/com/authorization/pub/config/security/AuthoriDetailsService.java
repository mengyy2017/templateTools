package com.authorization.pub.config.security;

import com.authorization.bussiness.entity.UserSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthoriDetailsService implements UserDetailsService {

//    @Autowired
//    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity userEntity = userService.selectUserAndRole(BuildUtil.newAndSet(UserEntity::new, BuildUtil.getVAndF(username, UserEntity::setUsername)));
//
//        List<SimpleGrantedAuthority> listGrantAuthority = userEntity.getRoleEntityList().stream()
//                .map(RoleEntity::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

//        UserSecurity userSecurity = new UserSecurity(userEntity.getUsername(), userEntity.getPassword(), listGrantAuthority);
        UserSecurity userSecurity = new UserSecurity("admin", "123456", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return userSecurity;
    }



}
