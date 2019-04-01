package com.authorization.service;

import com.authorization.base.service.CommonService;
import com.authorization.dao.UserMapper;
import com.authorization.entity.UserEntity;
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
