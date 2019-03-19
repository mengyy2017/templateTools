package com.templateTools.service;

import com.templateTools.base.service.CommonService;
import com.templateTools.dao.UserMapper;
import com.templateTools.entity.UserEntity;
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
