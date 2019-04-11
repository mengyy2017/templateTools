// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package com.grpc.proto.user;

/**
 * Protobuf type {@code UserRoleMsg}
 */
public  final class UserRoleMsg extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:UserRoleMsg)
    UserRoleMsgOrBuilder {
  // Use UserRoleMsg.newBuilder() to construct.
  private UserRoleMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UserRoleMsg() {
    id_ = "";
    username_ = "";
    password_ = "";
    roleMsgList_ = java.util.Collections.emptyList();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private UserRoleMsg(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            String s = input.readStringRequireUtf8();

            id_ = s;
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            username_ = s;
            break;
          }
          case 26: {
            String s = input.readStringRequireUtf8();

            password_ = s;
            break;
          }
          case 34: {
            if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
              roleMsgList_ = new java.util.ArrayList<RoleMsg>();
              mutable_bitField0_ |= 0x00000008;
            }
            roleMsgList_.add(
                input.readMessage(RoleMsg.parser(), extensionRegistry));
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
        roleMsgList_ = java.util.Collections.unmodifiableList(roleMsgList_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return UserProto.internal_static_UserRoleMsg_descriptor;
  }

  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return UserProto.internal_static_UserRoleMsg_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            UserRoleMsg.class, Builder.class);
  }

  private int bitField0_;
  public static final int ID_FIELD_NUMBER = 1;
  private volatile Object id_;
  /**
   * <code>optional string id = 1;</code>
   */
  public String getId() {
    Object ref = id_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      id_ = s;
      return s;
    }
  }
  /**
   * <code>optional string id = 1;</code>
   */
  public com.google.protobuf.ByteString
      getIdBytes() {
    Object ref = id_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      id_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int USERNAME_FIELD_NUMBER = 2;
  private volatile Object username_;
  /**
   * <code>optional string username = 2;</code>
   */
  public String getUsername() {
    Object ref = username_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      username_ = s;
      return s;
    }
  }
  /**
   * <code>optional string username = 2;</code>
   */
  public com.google.protobuf.ByteString
      getUsernameBytes() {
    Object ref = username_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      username_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PASSWORD_FIELD_NUMBER = 3;
  private volatile Object password_;
  /**
   * <code>optional string password = 3;</code>
   */
  public String getPassword() {
    Object ref = password_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      password_ = s;
      return s;
    }
  }
  /**
   * <code>optional string password = 3;</code>
   */
  public com.google.protobuf.ByteString
      getPasswordBytes() {
    Object ref = password_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      password_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ROLEMSGLIST_FIELD_NUMBER = 4;
  private java.util.List<RoleMsg> roleMsgList_;
  /**
   * <code>repeated .RoleMsg RoleMsgList = 4;</code>
   */
  public java.util.List<RoleMsg> getRoleMsgListList() {
    return roleMsgList_;
  }
  /**
   * <code>repeated .RoleMsg RoleMsgList = 4;</code>
   */
  public java.util.List<? extends RoleMsgOrBuilder>
      getRoleMsgListOrBuilderList() {
    return roleMsgList_;
  }
  /**
   * <code>repeated .RoleMsg RoleMsgList = 4;</code>
   */
  public int getRoleMsgListCount() {
    return roleMsgList_.size();
  }
  /**
   * <code>repeated .RoleMsg RoleMsgList = 4;</code>
   */
  public RoleMsg getRoleMsgList(int index) {
    return roleMsgList_.get(index);
  }
  /**
   * <code>repeated .RoleMsg RoleMsgList = 4;</code>
   */
  public RoleMsgOrBuilder getRoleMsgListOrBuilder(
      int index) {
    return roleMsgList_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, id_);
    }
    if (!getUsernameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, username_);
    }
    if (!getPasswordBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, password_);
    }
    for (int i = 0; i < roleMsgList_.size(); i++) {
      output.writeMessage(4, roleMsgList_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, id_);
    }
    if (!getUsernameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, username_);
    }
    if (!getPasswordBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, password_);
    }
    for (int i = 0; i < roleMsgList_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(4, roleMsgList_.get(i));
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof UserRoleMsg)) {
      return super.equals(obj);
    }
    UserRoleMsg other = (UserRoleMsg) obj;

    boolean result = true;
    result = result && getId()
        .equals(other.getId());
    result = result && getUsername()
        .equals(other.getUsername());
    result = result && getPassword()
        .equals(other.getPassword());
    result = result && getRoleMsgListList()
        .equals(other.getRoleMsgListList());
    return result;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + getId().hashCode();
    hash = (37 * hash) + USERNAME_FIELD_NUMBER;
    hash = (53 * hash) + getUsername().hashCode();
    hash = (37 * hash) + PASSWORD_FIELD_NUMBER;
    hash = (53 * hash) + getPassword().hashCode();
    if (getRoleMsgListCount() > 0) {
      hash = (37 * hash) + ROLEMSGLIST_FIELD_NUMBER;
      hash = (53 * hash) + getRoleMsgListList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static UserRoleMsg parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static UserRoleMsg parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static UserRoleMsg parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static UserRoleMsg parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static UserRoleMsg parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static UserRoleMsg parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static UserRoleMsg parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static UserRoleMsg parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static UserRoleMsg parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static UserRoleMsg parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(UserRoleMsg prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code UserRoleMsg}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:UserRoleMsg)
      com.grpc.proto.user.UserRoleMsgOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return UserProto.internal_static_UserRoleMsg_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return UserProto.internal_static_UserRoleMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              UserRoleMsg.class, Builder.class);
    }

    // Construct using com.grpc.proto.user.UserRoleMsg.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getRoleMsgListFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      id_ = "";

      username_ = "";

      password_ = "";

      if (roleMsgListBuilder_ == null) {
        roleMsgList_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000008);
      } else {
        roleMsgListBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return UserProto.internal_static_UserRoleMsg_descriptor;
    }

    public UserRoleMsg getDefaultInstanceForType() {
      return UserRoleMsg.getDefaultInstance();
    }

    public UserRoleMsg build() {
      UserRoleMsg result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public UserRoleMsg buildPartial() {
      UserRoleMsg result = new UserRoleMsg(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.id_ = id_;
      result.username_ = username_;
      result.password_ = password_;
      if (roleMsgListBuilder_ == null) {
        if (((bitField0_ & 0x00000008) == 0x00000008)) {
          roleMsgList_ = java.util.Collections.unmodifiableList(roleMsgList_);
          bitField0_ = (bitField0_ & ~0x00000008);
        }
        result.roleMsgList_ = roleMsgList_;
      } else {
        result.roleMsgList_ = roleMsgListBuilder_.build();
      }
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof UserRoleMsg) {
        return mergeFrom((UserRoleMsg)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(UserRoleMsg other) {
      if (other == UserRoleMsg.getDefaultInstance()) return this;
      if (!other.getId().isEmpty()) {
        id_ = other.id_;
        onChanged();
      }
      if (!other.getUsername().isEmpty()) {
        username_ = other.username_;
        onChanged();
      }
      if (!other.getPassword().isEmpty()) {
        password_ = other.password_;
        onChanged();
      }
      if (roleMsgListBuilder_ == null) {
        if (!other.roleMsgList_.isEmpty()) {
          if (roleMsgList_.isEmpty()) {
            roleMsgList_ = other.roleMsgList_;
            bitField0_ = (bitField0_ & ~0x00000008);
          } else {
            ensureRoleMsgListIsMutable();
            roleMsgList_.addAll(other.roleMsgList_);
          }
          onChanged();
        }
      } else {
        if (!other.roleMsgList_.isEmpty()) {
          if (roleMsgListBuilder_.isEmpty()) {
            roleMsgListBuilder_.dispose();
            roleMsgListBuilder_ = null;
            roleMsgList_ = other.roleMsgList_;
            bitField0_ = (bitField0_ & ~0x00000008);
            roleMsgListBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getRoleMsgListFieldBuilder() : null;
          } else {
            roleMsgListBuilder_.addAllMessages(other.roleMsgList_);
          }
        }
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      UserRoleMsg parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (UserRoleMsg) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private Object id_ = "";
    /**
     * <code>optional string id = 1;</code>
     */
    public String getId() {
      Object ref = id_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        id_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string id = 1;</code>
     */
    public com.google.protobuf.ByteString
        getIdBytes() {
      Object ref = id_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        id_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string id = 1;</code>
     */
    public Builder setId(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string id = 1;</code>
     */
    public Builder clearId() {
      
      id_ = getDefaultInstance().getId();
      onChanged();
      return this;
    }
    /**
     * <code>optional string id = 1;</code>
     */
    public Builder setIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      id_ = value;
      onChanged();
      return this;
    }

    private Object username_ = "";
    /**
     * <code>optional string username = 2;</code>
     */
    public String getUsername() {
      Object ref = username_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        username_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string username = 2;</code>
     */
    public com.google.protobuf.ByteString
        getUsernameBytes() {
      Object ref = username_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        username_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string username = 2;</code>
     */
    public Builder setUsername(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      username_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string username = 2;</code>
     */
    public Builder clearUsername() {
      
      username_ = getDefaultInstance().getUsername();
      onChanged();
      return this;
    }
    /**
     * <code>optional string username = 2;</code>
     */
    public Builder setUsernameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      username_ = value;
      onChanged();
      return this;
    }

    private Object password_ = "";
    /**
     * <code>optional string password = 3;</code>
     */
    public String getPassword() {
      Object ref = password_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        password_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string password = 3;</code>
     */
    public com.google.protobuf.ByteString
        getPasswordBytes() {
      Object ref = password_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        password_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string password = 3;</code>
     */
    public Builder setPassword(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      password_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string password = 3;</code>
     */
    public Builder clearPassword() {
      
      password_ = getDefaultInstance().getPassword();
      onChanged();
      return this;
    }
    /**
     * <code>optional string password = 3;</code>
     */
    public Builder setPasswordBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      password_ = value;
      onChanged();
      return this;
    }

    private java.util.List<RoleMsg> roleMsgList_ =
      java.util.Collections.emptyList();
    private void ensureRoleMsgListIsMutable() {
      if (!((bitField0_ & 0x00000008) == 0x00000008)) {
        roleMsgList_ = new java.util.ArrayList<RoleMsg>(roleMsgList_);
        bitField0_ |= 0x00000008;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        RoleMsg, RoleMsg.Builder, RoleMsgOrBuilder> roleMsgListBuilder_;

    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public java.util.List<RoleMsg> getRoleMsgListList() {
      if (roleMsgListBuilder_ == null) {
        return java.util.Collections.unmodifiableList(roleMsgList_);
      } else {
        return roleMsgListBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public int getRoleMsgListCount() {
      if (roleMsgListBuilder_ == null) {
        return roleMsgList_.size();
      } else {
        return roleMsgListBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public RoleMsg getRoleMsgList(int index) {
      if (roleMsgListBuilder_ == null) {
        return roleMsgList_.get(index);
      } else {
        return roleMsgListBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder setRoleMsgList(
        int index, RoleMsg value) {
      if (roleMsgListBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRoleMsgListIsMutable();
        roleMsgList_.set(index, value);
        onChanged();
      } else {
        roleMsgListBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder setRoleMsgList(
        int index, RoleMsg.Builder builderForValue) {
      if (roleMsgListBuilder_ == null) {
        ensureRoleMsgListIsMutable();
        roleMsgList_.set(index, builderForValue.build());
        onChanged();
      } else {
        roleMsgListBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder addRoleMsgList(RoleMsg value) {
      if (roleMsgListBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRoleMsgListIsMutable();
        roleMsgList_.add(value);
        onChanged();
      } else {
        roleMsgListBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder addRoleMsgList(
        int index, RoleMsg value) {
      if (roleMsgListBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRoleMsgListIsMutable();
        roleMsgList_.add(index, value);
        onChanged();
      } else {
        roleMsgListBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder addRoleMsgList(
        RoleMsg.Builder builderForValue) {
      if (roleMsgListBuilder_ == null) {
        ensureRoleMsgListIsMutable();
        roleMsgList_.add(builderForValue.build());
        onChanged();
      } else {
        roleMsgListBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder addRoleMsgList(
        int index, RoleMsg.Builder builderForValue) {
      if (roleMsgListBuilder_ == null) {
        ensureRoleMsgListIsMutable();
        roleMsgList_.add(index, builderForValue.build());
        onChanged();
      } else {
        roleMsgListBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder addAllRoleMsgList(
        Iterable<? extends RoleMsg> values) {
      if (roleMsgListBuilder_ == null) {
        ensureRoleMsgListIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, roleMsgList_);
        onChanged();
      } else {
        roleMsgListBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder clearRoleMsgList() {
      if (roleMsgListBuilder_ == null) {
        roleMsgList_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
      } else {
        roleMsgListBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public Builder removeRoleMsgList(int index) {
      if (roleMsgListBuilder_ == null) {
        ensureRoleMsgListIsMutable();
        roleMsgList_.remove(index);
        onChanged();
      } else {
        roleMsgListBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public RoleMsg.Builder getRoleMsgListBuilder(
        int index) {
      return getRoleMsgListFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public RoleMsgOrBuilder getRoleMsgListOrBuilder(
        int index) {
      if (roleMsgListBuilder_ == null) {
        return roleMsgList_.get(index);  } else {
        return roleMsgListBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public java.util.List<? extends RoleMsgOrBuilder>
         getRoleMsgListOrBuilderList() {
      if (roleMsgListBuilder_ != null) {
        return roleMsgListBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(roleMsgList_);
      }
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public RoleMsg.Builder addRoleMsgListBuilder() {
      return getRoleMsgListFieldBuilder().addBuilder(
          RoleMsg.getDefaultInstance());
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public RoleMsg.Builder addRoleMsgListBuilder(
        int index) {
      return getRoleMsgListFieldBuilder().addBuilder(
          index, RoleMsg.getDefaultInstance());
    }
    /**
     * <code>repeated .RoleMsg RoleMsgList = 4;</code>
     */
    public java.util.List<RoleMsg.Builder>
         getRoleMsgListBuilderList() {
      return getRoleMsgListFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        RoleMsg, RoleMsg.Builder, RoleMsgOrBuilder>
        getRoleMsgListFieldBuilder() {
      if (roleMsgListBuilder_ == null) {
        roleMsgListBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            RoleMsg, RoleMsg.Builder, RoleMsgOrBuilder>(
                roleMsgList_,
                ((bitField0_ & 0x00000008) == 0x00000008),
                getParentForChildren(),
                isClean());
        roleMsgList_ = null;
      }
      return roleMsgListBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:UserRoleMsg)
  }

  // @@protoc_insertion_point(class_scope:UserRoleMsg)
  private static final UserRoleMsg DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new UserRoleMsg();
  }

  public static UserRoleMsg getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UserRoleMsg>
      PARSER = new com.google.protobuf.AbstractParser<UserRoleMsg>() {
    public UserRoleMsg parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new UserRoleMsg(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UserRoleMsg> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<UserRoleMsg> getParserForType() {
    return PARSER;
  }

  public UserRoleMsg getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

