package com.system.bussiness.grpcImpl;

import com.grpc.proto.datasource.DatasourceMsg;
import com.grpc.service.DatasourceServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.factory.annotation.Value;

@GrpcService(DatasourceServiceGrpc.class)
public class DatasourceServiceGrpcImpl extends DatasourceServiceGrpc.DatasourceServiceImplBase {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void getDatasource(DatasourceMsg req, StreamObserver<DatasourceMsg> responseObserver) {
        DatasourceMsg datasourceMsg = DatasourceMsg.newBuilder().setUrl(url).setDriverClassName(driverClassName)
                .setUsername(username).setPassword(password).build();
        responseObserver.onNext(datasourceMsg);
        responseObserver.onCompleted();
    }
}
