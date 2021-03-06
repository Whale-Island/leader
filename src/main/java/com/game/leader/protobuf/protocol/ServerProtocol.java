// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Server.proto

package com.game.leader.protobuf.protocol;

public final class ServerProtocol {
  private ServerProtocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ReqHeartbeatMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ReqHeartbeatMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 uid = 1;</code>
     */
    int getUid();
  }
  /**
   * Protobuf type {@code ReqHeartbeatMessage}
   */
  public  static final class ReqHeartbeatMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ReqHeartbeatMessage)
      ReqHeartbeatMessageOrBuilder {
    // Use ReqHeartbeatMessage.newBuilder() to construct.
    private ReqHeartbeatMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ReqHeartbeatMessage() {
      uid_ = 0;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private ReqHeartbeatMessage(
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
            case 8: {

              uid_ = input.readInt32();
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
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.game.leader.protobuf.protocol.ServerProtocol.internal_static_ReqHeartbeatMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.game.leader.protobuf.protocol.ServerProtocol.internal_static_ReqHeartbeatMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.class, com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.Builder.class);
    }

    public static final int UID_FIELD_NUMBER = 1;
    private int uid_;
    /**
     * <code>int32 uid = 1;</code>
     */
    public int getUid() {
      return uid_;
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
      if (uid_ != 0) {
        output.writeInt32(1, uid_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (uid_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, uid_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage)) {
        return super.equals(obj);
      }
      com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage other = (com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage) obj;

      boolean result = true;
      result = result && (getUid()
          == other.getUid());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + UID_FIELD_NUMBER;
      hash = (53 * hash) + getUid();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parseFrom(
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
    public static Builder newBuilder(com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code ReqHeartbeatMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ReqHeartbeatMessage)
        com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.game.leader.protobuf.protocol.ServerProtocol.internal_static_ReqHeartbeatMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.game.leader.protobuf.protocol.ServerProtocol.internal_static_ReqHeartbeatMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.class, com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.Builder.class);
      }

      // Construct using com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        uid_ = 0;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.game.leader.protobuf.protocol.ServerProtocol.internal_static_ReqHeartbeatMessage_descriptor;
      }

      public com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage getDefaultInstanceForType() {
        return com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.getDefaultInstance();
      }

      public com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage build() {
        com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage buildPartial() {
        com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage result = new com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage(this);
        result.uid_ = uid_;
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
        if (other instanceof com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage) {
          return mergeFrom((com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage other) {
        if (other == com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage.getDefaultInstance()) return this;
        if (other.getUid() != 0) {
          setUid(other.getUid());
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
        com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int uid_ ;
      /**
       * <code>int32 uid = 1;</code>
       */
      public int getUid() {
        return uid_;
      }
      /**
       * <code>int32 uid = 1;</code>
       */
      public Builder setUid(int value) {
        
        uid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 uid = 1;</code>
       */
      public Builder clearUid() {
        
        uid_ = 0;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:ReqHeartbeatMessage)
    }

    // @@protoc_insertion_point(class_scope:ReqHeartbeatMessage)
    private static final com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage();
    }

    public static com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ReqHeartbeatMessage>
        PARSER = new com.google.protobuf.AbstractParser<ReqHeartbeatMessage>() {
      public ReqHeartbeatMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new ReqHeartbeatMessage(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ReqHeartbeatMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ReqHeartbeatMessage> getParserForType() {
      return PARSER;
    }

    public com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ReqHeartbeatMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ReqHeartbeatMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014Server.proto\"\"\n\023ReqHeartbeatMessage\022\013\n" +
      "\003uid\030\001 \001(\005B3\n!com.game.leader.protobuf.p" +
      "rotocolB\016ServerProtocolb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_ReqHeartbeatMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ReqHeartbeatMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ReqHeartbeatMessage_descriptor,
        new java.lang.String[] { "Uid", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
