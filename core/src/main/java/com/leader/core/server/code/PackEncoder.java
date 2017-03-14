package com.leader.core.server.code;

import java.nio.charset.Charset;
import java.util.List;

import com.google.protobuf.Message.Builder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class PackEncoder extends MessageToMessageEncoder<Builder> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Builder msg, List<Object> out) throws Exception {
		try {
			String type = msg.getDescriptorForType().getFullName();
			byte[] msgType = type.getBytes(Charset.forName("UTF-8"));
			byte[] data = msg.build().toByteArray();
			int msglen = 4 + 4 + msgType.length + data.length;
			ByteBuf buf = Unpooled.buffer(msglen);
			buf.writeInt(4 + msgType.length + data.length);
			buf.writeInt(msgType.length);
			buf.writeBytes(msgType);
			buf.writeBytes(data);
			out.add(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
