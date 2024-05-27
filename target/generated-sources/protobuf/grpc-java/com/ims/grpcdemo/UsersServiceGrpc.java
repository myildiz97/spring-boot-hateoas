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
  private static volatile io.grpc.MethodDescriptor<com.ims.grpcdemo.LoginUserRequest,
      com.ims.grpcdemo.LoginUserResponse> getLoginUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LoginUser",
      requestType = com.ims.grpcdemo.LoginUserRequest.class,
      responseType = com.ims.grpcdemo.LoginUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ims.grpcdemo.LoginUserRequest,
      com.ims.grpcdemo.LoginUserResponse> getLoginUserMethod() {
    io.grpc.MethodDescriptor<com.ims.grpcdemo.LoginUserRequest, com.ims.grpcdemo.LoginUserResponse> getLoginUserMethod;
    if ((getLoginUserMethod = UsersServiceGrpc.getLoginUserMethod) == null) {
      synchronized (UsersServiceGrpc.class) {
        if ((getLoginUserMethod = UsersServiceGrpc.getLoginUserMethod) == null) {
          UsersServiceGrpc.getLoginUserMethod = getLoginUserMethod =
              io.grpc.MethodDescriptor.<com.ims.grpcdemo.LoginUserRequest, com.ims.grpcdemo.LoginUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LoginUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ims.grpcdemo.LoginUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ims.grpcdemo.LoginUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UsersServiceMethodDescriptorSupplier("LoginUser"))
              .build();
        }
      }
    }
    return getLoginUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ims.grpcdemo.SignupUserRequest,
      com.ims.grpcdemo.SignupUserResponse> getSignupUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SignupUser",
      requestType = com.ims.grpcdemo.SignupUserRequest.class,
      responseType = com.ims.grpcdemo.SignupUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ims.grpcdemo.SignupUserRequest,
      com.ims.grpcdemo.SignupUserResponse> getSignupUserMethod() {
    io.grpc.MethodDescriptor<com.ims.grpcdemo.SignupUserRequest, com.ims.grpcdemo.SignupUserResponse> getSignupUserMethod;
    if ((getSignupUserMethod = UsersServiceGrpc.getSignupUserMethod) == null) {
      synchronized (UsersServiceGrpc.class) {
        if ((getSignupUserMethod = UsersServiceGrpc.getSignupUserMethod) == null) {
          UsersServiceGrpc.getSignupUserMethod = getSignupUserMethod =
              io.grpc.MethodDescriptor.<com.ims.grpcdemo.SignupUserRequest, com.ims.grpcdemo.SignupUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SignupUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ims.grpcdemo.SignupUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ims.grpcdemo.SignupUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UsersServiceMethodDescriptorSupplier("SignupUser"))
              .build();
        }
      }
    }
    return getSignupUserMethod;
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
     * Logs in a user.
     * </pre>
     */
    public void loginUser(com.ims.grpcdemo.LoginUserRequest request,
        io.grpc.stub.StreamObserver<com.ims.grpcdemo.LoginUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginUserMethod(), responseObserver);
    }

    /**
     * <pre>
     * Signs up a new user.
     * </pre>
     */
    public void signupUser(com.ims.grpcdemo.SignupUserRequest request,
        io.grpc.stub.StreamObserver<com.ims.grpcdemo.SignupUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSignupUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.ims.grpcdemo.LoginUserRequest,
                com.ims.grpcdemo.LoginUserResponse>(
                  this, METHODID_LOGIN_USER)))
          .addMethod(
            getSignupUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.ims.grpcdemo.SignupUserRequest,
                com.ims.grpcdemo.SignupUserResponse>(
                  this, METHODID_SIGNUP_USER)))
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
     * Logs in a user.
     * </pre>
     */
    public void loginUser(com.ims.grpcdemo.LoginUserRequest request,
        io.grpc.stub.StreamObserver<com.ims.grpcdemo.LoginUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Signs up a new user.
     * </pre>
     */
    public void signupUser(com.ims.grpcdemo.SignupUserRequest request,
        io.grpc.stub.StreamObserver<com.ims.grpcdemo.SignupUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSignupUserMethod(), getCallOptions()), request, responseObserver);
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
     * Logs in a user.
     * </pre>
     */
    public com.ims.grpcdemo.LoginUserResponse loginUser(com.ims.grpcdemo.LoginUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginUserMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Signs up a new user.
     * </pre>
     */
    public com.ims.grpcdemo.SignupUserResponse signupUser(com.ims.grpcdemo.SignupUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSignupUserMethod(), getCallOptions(), request);
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
     * Logs in a user.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ims.grpcdemo.LoginUserResponse> loginUser(
        com.ims.grpcdemo.LoginUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginUserMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Signs up a new user.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ims.grpcdemo.SignupUserResponse> signupUser(
        com.ims.grpcdemo.SignupUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSignupUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN_USER = 0;
  private static final int METHODID_SIGNUP_USER = 1;

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
        case METHODID_LOGIN_USER:
          serviceImpl.loginUser((com.ims.grpcdemo.LoginUserRequest) request,
              (io.grpc.stub.StreamObserver<com.ims.grpcdemo.LoginUserResponse>) responseObserver);
          break;
        case METHODID_SIGNUP_USER:
          serviceImpl.signupUser((com.ims.grpcdemo.SignupUserRequest) request,
              (io.grpc.stub.StreamObserver<com.ims.grpcdemo.SignupUserResponse>) responseObserver);
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
              .addMethod(getLoginUserMethod())
              .addMethod(getSignupUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
