package com.ims.grpcdemo;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The Users service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.43.0)",
    comments = "Source: users.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UsersServiceGrpc {

  private UsersServiceGrpc() {}

  public static final String SERVICE_NAME = "users.UsersService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.ims.grpcdemo.CapabilityOfUserRequest,
      com.ims.grpcdemo.CapabilityOfUserResponse> getCapabilityOfUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CapabilityOfUser",
      requestType = com.ims.grpcdemo.CapabilityOfUserRequest.class,
      responseType = com.ims.grpcdemo.CapabilityOfUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ims.grpcdemo.CapabilityOfUserRequest,
      com.ims.grpcdemo.CapabilityOfUserResponse> getCapabilityOfUserMethod() {
    io.grpc.MethodDescriptor<com.ims.grpcdemo.CapabilityOfUserRequest, com.ims.grpcdemo.CapabilityOfUserResponse> getCapabilityOfUserMethod;
    if ((getCapabilityOfUserMethod = UsersServiceGrpc.getCapabilityOfUserMethod) == null) {
      synchronized (UsersServiceGrpc.class) {
        if ((getCapabilityOfUserMethod = UsersServiceGrpc.getCapabilityOfUserMethod) == null) {
          UsersServiceGrpc.getCapabilityOfUserMethod = getCapabilityOfUserMethod =
              io.grpc.MethodDescriptor.<com.ims.grpcdemo.CapabilityOfUserRequest, com.ims.grpcdemo.CapabilityOfUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CapabilityOfUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ims.grpcdemo.CapabilityOfUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ims.grpcdemo.CapabilityOfUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UsersServiceMethodDescriptorSupplier("CapabilityOfUser"))
              .build();
        }
      }
    }
    return getCapabilityOfUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UsersServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UsersServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UsersServiceStub>() {
        @java.lang.Override
        public UsersServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UsersServiceStub(channel, callOptions);
        }
      };
    return UsersServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UsersServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UsersServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UsersServiceBlockingStub>() {
        @java.lang.Override
        public UsersServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UsersServiceBlockingStub(channel, callOptions);
        }
      };
    return UsersServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UsersServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UsersServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UsersServiceFutureStub>() {
        @java.lang.Override
        public UsersServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UsersServiceFutureStub(channel, callOptions);
        }
      };
    return UsersServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The Users service definition.
   * </pre>
   */
  public static abstract class UsersServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Returns the capabilities of the user.
     * </pre>
     */
    public void capabilityOfUser(com.ims.grpcdemo.CapabilityOfUserRequest request,
        io.grpc.stub.StreamObserver<com.ims.grpcdemo.CapabilityOfUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCapabilityOfUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCapabilityOfUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.ims.grpcdemo.CapabilityOfUserRequest,
                com.ims.grpcdemo.CapabilityOfUserResponse>(
                  this, METHODID_CAPABILITY_OF_USER)))
          .build();
    }
  }

  /**
   * <pre>
   * The Users service definition.
   * </pre>
   */
  public static final class UsersServiceStub extends io.grpc.stub.AbstractAsyncStub<UsersServiceStub> {
    private UsersServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsersServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UsersServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the capabilities of the user.
     * </pre>
     */
    public void capabilityOfUser(com.ims.grpcdemo.CapabilityOfUserRequest request,
        io.grpc.stub.StreamObserver<com.ims.grpcdemo.CapabilityOfUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCapabilityOfUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The Users service definition.
   * </pre>
   */
  public static final class UsersServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UsersServiceBlockingStub> {
    private UsersServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsersServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UsersServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the capabilities of the user.
     * </pre>
     */
    public com.ims.grpcdemo.CapabilityOfUserResponse capabilityOfUser(com.ims.grpcdemo.CapabilityOfUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCapabilityOfUserMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The Users service definition.
   * </pre>
   */
  public static final class UsersServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UsersServiceFutureStub> {
    private UsersServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsersServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UsersServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the capabilities of the user.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ims.grpcdemo.CapabilityOfUserResponse> capabilityOfUser(
        com.ims.grpcdemo.CapabilityOfUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCapabilityOfUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CAPABILITY_OF_USER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UsersServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UsersServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CAPABILITY_OF_USER:
          serviceImpl.capabilityOfUser((com.ims.grpcdemo.CapabilityOfUserRequest) request,
              (io.grpc.stub.StreamObserver<com.ims.grpcdemo.CapabilityOfUserResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UsersServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UsersServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.ims.grpcdemo.Users.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UsersService");
    }
  }

  private static final class UsersServiceFileDescriptorSupplier
      extends UsersServiceBaseDescriptorSupplier {
    UsersServiceFileDescriptorSupplier() {}
  }

  private static final class UsersServiceMethodDescriptorSupplier
      extends UsersServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UsersServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UsersServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UsersServiceFileDescriptorSupplier())
              .addMethod(getCapabilityOfUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
