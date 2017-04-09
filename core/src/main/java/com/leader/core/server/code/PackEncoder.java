package com.leader.core.server.code;

import java.util.List;

import com.google.protobuf.Message.Builder;
import com.leader.core.server.model.DynamicMessageFactory;
import com.leader.core.util.ByteKit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class PackEncoder extends MessageToMessageEncoder<Builder> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Builder msg, List<Object> out) throws Exception {
		try {
			String name = msg.getDescriptorForType().getFullName();
			int type = DynamicMessageFactory.getDescriptor(name);
			byte[] msgType = new byte[4];
			ByteKit.writeInt(type, msgType, 0);
			byte[] data = msg.build().toByteArray();
			int msglen = 4 + 4 + data.length;
			ByteBuf buf = Unpooled.buffer(msglen);
			buf.writeInt(4 + 4 + data.length);
			buf.writeBytes(msgType);
			buf.writeBytes(data);
			out.add(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
