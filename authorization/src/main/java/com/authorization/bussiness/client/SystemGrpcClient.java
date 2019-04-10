package com.authorization.bussiness.client;

import io.grpc.Channel;
import net.devh.examples.grpc.lib.HelloReply;
import net.devh.examples.grpc.lib.HelloRequest;
import net.devh.examples.grpc.lib.SimpleGrpc;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SystemGrpcClient {

    @GrpcClient("systemGrpcServer")
    private Channel systemChannel;

    public String sayHello(String name){
        SimpleGrpc.SimpleBlockingStub stub = SimpleGrpc.newBlockingStub(systemChannel);
        HelloReply resp = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return resp.getMessage();
    }


}
