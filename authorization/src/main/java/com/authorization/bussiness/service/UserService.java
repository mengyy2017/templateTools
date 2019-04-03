package com.authorization.bussiness.service;

import com.authorization.bussiness.dao.UserMapper;
import com.authorization.bussiness.entity.UserEntity;
import com.common.bussiness.service.CommonService;
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
