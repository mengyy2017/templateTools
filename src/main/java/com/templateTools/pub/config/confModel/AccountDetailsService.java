package com.templateTools.pub.config.confModel;

import com.templateTools.entity.MenuEntity;
import com.templateTools.entity.RoleEntity;
import com.templateTools.entity.UserEntity;
import com.templateTools.entity.model.UserSecurity;
import com.templateTools.service.MenuService;
import com.templateTools.service.UserService;
import com.templateTools.utils.BuildUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDetailsService extends BuildUtil implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    // 只有访问设置的loginProcessingUrl那个地址 才会调用这个方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userService.selectUserAndRole(newAndSet(UserEntity::new, getVAndF(username, UserEntity::setUsername)));
//        UserEntity userEntity = userService.selectOne(newAndSet(UserEntity::new, getVAndF(username, UserEntity::setUsername)));

        UserSecurity userSecurity = new UserSecurity(userEntity.getUsername(), userEntity.getPassword()
                                        , userEntity.getRoleEntityList().stream().map(RoleEntity::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        setUrlPermission();

        return userSecurity;
    }

    private void setUrlPermission() {

        if (MetadataSource.sysMenuPerMap == null) {
            List<MenuEntity> sysMenuList = menuService.selectAll();

            LinkedList<String> linkedList = sysMenuList.parallelStream().collect(LinkedList::new, (l, e) ->
            { l.add(e.getUrl()); l.add(e.getPermission()); }, (l1, l2) -> l1.addAll(l2));

            MetadataSource.sysMenuPerMap = BuildUtil.putsValsLoop(new HashMap<>(), HashMap<String, String>::put, linkedList);

        }
    }


}
