package system.bussiness.dao;

import com.common.bussiness.mapper.CommonMapper;
import system.bussiness.entity.UserEntity;

public interface UserMapper extends CommonMapper<UserEntity> {

    UserEntity selectUserAndRoles(UserEntity userEntity);
}
