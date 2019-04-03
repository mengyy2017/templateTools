package com.authorization.bussiness.dao;

import com.authorization.bussiness.entity.UserEntity;
import com.common.bussiness.mapper.CommonMapper;

public interface UserMapper extends CommonMapper<UserEntity> {

    UserEntity selectUserAndRoles(UserEntity userEntity);
}
