package com.templateTools.dao;

import com.templateTools.base.mapper.CommonMapper;
import com.templateTools.entity.UserEntity;

public interface UserMapper extends CommonMapper<UserEntity> {

    UserEntity selectUserAndRoles(UserEntity userEntity);
}
