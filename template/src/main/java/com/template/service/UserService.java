package com.template.service;

import com.template.base.service.CommonService;
import com.template.dao.UserMapper;
import com.template.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CommonService<UserEntity> {

    private UserMapper getMapper() {
        return (UserMapper) this.commonMapper;
    }

    public UserEntity selectUserAndRole(UserEntity userEntity) {
        return getMapper().selectUserAndRoles(userEntity);
    }
}
