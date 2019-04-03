package com.template.dao;

import com.template.base.mapper.CommonMapper;
import com.template.entity.UserEntity;

public interface UserMapper extends CommonMapper<UserEntity> {

    UserEntity selectUserAndRoles(UserEntity userEntity);
}
