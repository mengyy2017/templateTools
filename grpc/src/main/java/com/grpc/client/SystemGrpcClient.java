package com.grpc.client;

import com.google.common.collect.Lists;
import com.grpc.proto.datasource.DatasourceMsg;
import com.grpc.proto.hello.HelloReply;
import com.grpc.proto.hello.HelloRequest;
import com.grpc.proto.menu.MenuMsg;
import com.grpc.proto.user.UserRoleMsg;
import com.grpc.service.DatasourceServiceGrpc;
import com.grpc.service.HelloWorldServiceGrpc;
import com.grpc.service.MenuServiceGrpc;
import com.grpc.service.UserServiceGrpc;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SystemGrpcClient {

    @GrpcClient("systemGrpcServer")
    private Channel systemChannel;

    public String sayHello(String name){
        HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(systemChannel);
        HelloReply resp = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return resp.getMessage();
    }

    public UserRoleMsg getUser(UserRoleMsg userRoleMsg){
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(systemChannel);
        userRoleMsg = stub.getUser(userRoleMsg);
        return userRoleMsg;
    }

    public List<MenuMsg> getAllMenu(MenuMsg menuMsg){
        MenuServiceGrpc.MenuServiceBlockingStub stub = MenuServiceGrpc.newBlockingStub(systemChannel);
        List<MenuMsg> menuList = Lists.newArrayList(stub.getAllMenu(menuMsg));
        return menuList;
    }

    public DatasourceMsg getDatasource(DatasourceMsg datasourceMsg){
        DatasourceServiceGrpc.DatasourceServiceBlockingStub stub = DatasourceServiceGrpc.newBlockingStub(systemChannel);
        return stub.getDatasource(datasourceMsg);
    }

}
