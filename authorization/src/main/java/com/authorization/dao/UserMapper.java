package com.authorization.dao;

import com.authorization.base.mapper.CommonMapper;
import com.authorization.entity.UserEntity;

public interface UserMapper extends CommonMapper<UserEntity> {

    UserEntity selectUserAndRoles(UserEntity userEntity);
}
