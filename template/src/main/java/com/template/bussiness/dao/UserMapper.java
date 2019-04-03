package com.template.bussiness.dao;

import com.common.bussiness.mapper.CommonMapper;
import com.template.bussiness.entity.UserEntity;

public interface UserMapper extends CommonMapper<UserEntity> {

    UserEntity selectUserAndRoles(UserEntity userEntity);
}
