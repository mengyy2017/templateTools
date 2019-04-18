package com.grpc.service;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0-pre2)",
    comments = "Source: datasource.proto")
public class DatasourceServiceGrpc {

  private DatasourceServiceGrpc() {}

  public static final String SERVICE_NAME = "DatasourceService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.grpc.proto.datasource.DatasourceMsg,
      com.grpc.proto.datasource.DatasourceMsg> METHOD_GET_DATASOURCE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "DatasourceService", "GetDatasource"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.proto.datasource.DatasourceMsg.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.proto.datasource.DatasourceMsg.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DatasourceServiceStub newStub(io.grpc.Channel channel) {
    return new DatasourceServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DatasourceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DatasourceServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static DatasourceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DatasourceServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class DatasourceServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getDatasource(com.grpc.proto.datasource.DatasourceMsg request,
        io.grpc.stub.StreamObserver<com.grpc.proto.datasource.DatasourceMsg> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DATASOURCE, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_DATASOURCE,
            asyncUnaryCall(
              new MethodHandlers<
                com.grpc.proto.datasource.DatasourceMsg,
                com.grpc.proto.datasource.DatasourceMsg>(
                  this, METHODID_GET_DATASOURCE)))
          .build();
    }
  }

  /**
   */
  public static final class DatasourceServiceStub extends io.grpc.stub.AbstractStub<DatasourceServiceStub> {
    private DatasourceServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DatasourceServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DatasourceServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DatasourceServiceStub(channel, callOptions);
    }

    /**
     */
    public void getDatasource(com.grpc.proto.datasource.DatasourceMsg request,
        io.grpc.stub.StreamObserver<com.grpc.proto.datasource.DatasourceMsg> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DATASOURCE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DatasourceServiceBlockingStub extends io.grpc.stub.AbstractStub<DatasourceServiceBlockingStub> {
    private DatasourceServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DatasourceServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DatasourceServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DatasourceServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.grpc.proto.datasource.DatasourceMsg getDatasource(com.grpc.proto.datasource.DatasourceMsg request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DATASOURCE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DatasourceServiceFutureStub extends io.grpc.stub.AbstractStub<DatasourceServiceFutureStub> {
    private DatasourceServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DatasourceServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DatasourceServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DatasourceServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.grpc.proto.datasource.DatasourceMsg> getDatasource(
        com.grpc.proto.datasource.DatasourceMsg request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DATASOURCE, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DATASOURCE = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DatasourceServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(DatasourceServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DATASOURCE:
          serviceImpl.getDatasource((com.grpc.proto.datasource.DatasourceMsg) request,
              (io.grpc.stub.StreamObserver<com.grpc.proto.datasource.DatasourceMsg>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_GET_DATASOURCE);
  }

}
