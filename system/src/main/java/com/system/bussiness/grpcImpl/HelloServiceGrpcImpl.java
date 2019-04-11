package com.system.bussiness.grpcImpl;

import com.grpc.proto.hello.HelloReply;
import com.grpc.proto.hello.HelloRequest;
import com.grpc.service.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(HelloWorldServiceGrpc.class)
public class HelloServiceGrpcImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello =============> " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
