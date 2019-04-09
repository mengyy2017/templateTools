package system.bussiness.service;

import com.common.bussiness.service.CommonService;
import org.springframework.stereotype.Service;
import system.bussiness.dao.UserMapper;
import system.bussiness.entity.UserEntity;

@Service
public class UserService extends CommonService<UserEntity> {

    private UserMapper getMapper() {
        return (UserMapper) this.commonMapper;
    }

    public UserEntity selectUserAndRole(UserEntity userEntity) {
        return getMapper().selectUserAndRoles(userEntity);
    }
}
