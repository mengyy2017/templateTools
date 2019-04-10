package com.grpc.client;

import com.grpc.proto.HelloReply;
import com.grpc.proto.HelloRequest;
import com.grpc.service.HelloWorldServiceGrpc;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SystemGrpcClient {

    @GrpcClient("systemGrpcServer")
    private Channel systemChannel;

    public String sayHello(String name){
        HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(systemChannel);
        HelloReply resp = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return resp.getMessage();
    }


}
