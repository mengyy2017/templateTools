package com.system.bussiness.grpcImpl;

import com.common.util.BuildUtil;
import com.grpc.proto.menu.MenuMsg;
import com.grpc.service.MenuServiceGrpc;
import com.system.bussiness.entity.MenuEntity;
import com.system.bussiness.service.MenuService;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@GrpcService(MenuServiceGrpc.class)
public class MenuServiceGrpcImpl extends MenuServiceGrpc.MenuServiceImplBase {

    @Autowired
    private MenuService menuService;

    @Override
    public void getAllMenu(MenuMsg request, StreamObserver<MenuMsg> responseObserver) {
        List<MenuEntity> menuList = menuService.select(BuildUtil.newAndSet0(MenuEntity::new, new String[]{request.getModule()}, MenuEntity::setModule));
        menuList.stream().forEach(menuEntity -> responseObserver.onNext(
                MenuMsg.newBuilder().setId(menuEntity.getId()).setUrl(menuEntity.getUrl()).setPermission(menuEntity.getPermission()).setModule(menuEntity.getModule()).build()
        ));
        responseObserver.onCompleted();
    }
}
