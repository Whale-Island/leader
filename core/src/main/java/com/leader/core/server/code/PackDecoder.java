package com.leader.core.server.code;

import java.util.List;

import com.leader.core.server.model.Package;
import com.leader.core.util.ByteKit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PackDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		if (!msg.isReadable()) {
			return;
		}
		for (;;) {
			if (msg.readableBytes() < 4) {
				return;
			}
			msg.markReaderIndex();
			int msglen = msg.readInt();
			if (msglen > msg.readableBytes()) {
				msg.resetReaderIndex();
				return;
			}
			byte[] descriptor = new byte[2];
			msg.readBytes(descriptor);
			byte[] data = new byte[msglen - 2 - 2];
			msg.readBytes(data);
			Package pk = new Package();
			pk.setDescriptor(ByteKit.readInt(descriptor, 0));
			pk.setData(data);
			out.add(pk);
		}
	}
}
