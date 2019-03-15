package com.templateTools.pub.config.confModel;

import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.SysMenuEntity;
import com.templateTools.entity.UserEntity;
import com.templateTools.entity.model.UserSecurity;
import com.templateTools.service.SysMenuService;
import com.templateTools.service.UserService;
import com.templateTools.utils.BuildUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private static SysMenuService sysMenuService;

    // 只有访问设置的loginProcessingUrl那个地址 才会调用这个方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = BuildUtil.newAndSet(UserEntity::new, BuildUtil.getVAndF("admin", UserEntity::setUsername));
        userEntity = userService.selectOne(userEntity);

        setUrlPermission();

        UserSecurity userSecurity = new UserSecurity(userEntity.getUsername(), userEntity.getPassword(),  Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return userSecurity;
    }

    private void setUrlPermission() {

        if (MetadataSource.sysMenuPerMap == null) {
            List<SysMenuEntity> sysMenuList = sysMenuService.selectAll();

            LinkedList<String> linkedList = sysMenuList.parallelStream().collect(LinkedList::new, (l, e) ->
            { l.add(e.getUrl()); l.add(e.getPermission()); }, (l1, l2) -> l1.addAll(l2));

            MetadataSource.sysMenuPerMap = BuildUtil.putsValsLoop(new HashMap<>(), HashMap<String, String>::put, linkedList);

        }
    }


}
