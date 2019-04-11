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
    comments = "Source: menu.proto")
public class MenuServiceGrpc {

  private MenuServiceGrpc() {}

  public static final String SERVICE_NAME = "MenuService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.grpc.proto.menu.MenuMsg,
      com.grpc.proto.menu.MenuMsg> METHOD_GET_ALL_MENU =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "MenuService", "GetAllMenu"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.proto.menu.MenuMsg.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.grpc.proto.menu.MenuMsg.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MenuServiceStub newStub(io.grpc.Channel channel) {
    return new MenuServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MenuServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MenuServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static MenuServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MenuServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MenuServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllMenu(com.grpc.proto.menu.MenuMsg request,
        io.grpc.stub.StreamObserver<com.grpc.proto.menu.MenuMsg> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_MENU, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_ALL_MENU,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.grpc.proto.menu.MenuMsg,
                com.grpc.proto.menu.MenuMsg>(
                  this, METHODID_GET_ALL_MENU)))
          .build();
    }
  }

  /**
   */
  public static final class MenuServiceStub extends io.grpc.stub.AbstractStub<MenuServiceStub> {
    private MenuServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MenuServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MenuServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MenuServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAllMenu(com.grpc.proto.menu.MenuMsg request,
        io.grpc.stub.StreamObserver<com.grpc.proto.menu.MenuMsg> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_ALL_MENU, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MenuServiceBlockingStub extends io.grpc.stub.AbstractStub<MenuServiceBlockingStub> {
    private MenuServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MenuServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MenuServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MenuServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.grpc.proto.menu.MenuMsg> getAllMenu(
        com.grpc.proto.menu.MenuMsg request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_ALL_MENU, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MenuServiceFutureStub extends io.grpc.stub.AbstractStub<MenuServiceFutureStub> {
    private MenuServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MenuServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MenuServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MenuServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_ALL_MENU = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MenuServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(MenuServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_MENU:
          serviceImpl.getAllMenu((com.grpc.proto.menu.MenuMsg) request,
              (io.grpc.stub.StreamObserver<com.grpc.proto.menu.MenuMsg>) responseObserver);
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
        METHOD_GET_ALL_MENU);
  }

}
