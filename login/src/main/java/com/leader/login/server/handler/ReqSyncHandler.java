package com.leader.login.server.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqSyncMessage;
import com.leader.login.sync.SyncFutureManager;

import io.netty.channel.Channel;

@Controller
@Protocol("LoginProtocol")
public class ReqSyncHandler implements Handler {
	@Autowired
	SyncFutureManager syncFutureManager;

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		syncFutureManager.action(channel, (ReqSyncMessage) m);
	}

}
