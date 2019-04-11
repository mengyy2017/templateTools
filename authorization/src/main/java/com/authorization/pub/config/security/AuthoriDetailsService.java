package com.authorization.pub.config.security;

import com.grpc.client.SystemGrpcClient;
import com.grpc.proto.user.RoleMsg;
import com.grpc.proto.user.UserRoleMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthoriDetailsService implements UserDetailsService {

    @Autowired
    private SystemGrpcClient systemGrpcClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRoleMsg userRoleMsg = systemGrpcClient.getUser(UserRoleMsg.newBuilder().setUsername(username).build());

        List<SimpleGrantedAuthority> grantedAuthorityList = userRoleMsg.getRoleMsgListList()
                .stream().map(RoleMsg::getCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User userSecurity = new User(userRoleMsg.getUsername(), userRoleMsg.getPassword(), grantedAuthorityList);

        return userSecurity;
    }

}
