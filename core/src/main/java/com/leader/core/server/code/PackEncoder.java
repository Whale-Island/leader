package com.leader.core.server.code;

import java.util.List;

import com.google.protobuf.Message.Builder;
import com.leader.core.server.model.DynamicMessageFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class PackEncoder extends MessageToMessageEncoder<Builder> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Builder msg, List<Object> out) throws Exception {
		try {
			String name = msg.getDescriptorForType().getFullName();
			short type = DynamicMessageFactory.getDescriptor(name);
			byte[] data = msg.build().toByteArray();
			short msglen = (short) (2 + data.length);
			ByteBuf buf = Unpooled.buffer(msglen);
			buf.writeShort(msglen);
			buf.writeShort(type);
			buf.writeBytes(data);
			out.add(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
