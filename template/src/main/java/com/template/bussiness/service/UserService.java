package com.template.bussiness.service;

import com.common.bussiness.service.CommonService;
import com.template.bussiness.dao.UserMapper;
import com.template.bussiness.entity.UserEntity;
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
