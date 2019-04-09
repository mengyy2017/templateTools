package com.authorization.pub.config.security;

import com.authorization.bussiness.entity.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
        UserSecurity userSecurity = new UserSecurity("123", "456", null);

        return userSecurity;
    }



}
