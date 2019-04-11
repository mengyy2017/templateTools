package com.system.bussiness.grpcImpl;

import com.common.util.BuildUtil;
import com.grpc.proto.user.RoleMsg;
import com.grpc.proto.user.UserRoleMsg;
import com.grpc.service.UserServiceGrpc;
import com.system.bussiness.entity.UserEntity;
import com.system.bussiness.service.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService(UserServiceGrpc.class)
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserService userService;

    @Override
    public void getUser(UserRoleMsg request, StreamObserver<UserRoleMsg> responseObserver) {
        UserEntity userEntity = userService.selectUserAndRole(BuildUtil.newAndSet0(
                UserEntity::new, new String[]{request.getUsername()}, UserEntity::setUsername
        ));

        List<RoleMsg> roleMsgList = userEntity.getRoleEntityList().stream().map(roleEntity ->
                RoleMsg.newBuilder().setId(roleEntity.getId()).setCode(roleEntity.getRoleCode())
                        .setName(roleEntity.getRoleName()).build()
        ).collect(Collectors.toList());

        UserRoleMsg userRoleMsg = UserRoleMsg.newBuilder().setId(userEntity.getId()).setUsername(userEntity.getUsername())
                .setPassword(userEntity.getPassword()).addAllRoleMsgList(roleMsgList).build();

        responseObserver.onNext(userRoleMsg);
        responseObserver.onCompleted();
    }
}
