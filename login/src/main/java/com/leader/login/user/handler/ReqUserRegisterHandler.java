package com.leader.login.user.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqUserRegisterMessage;
import com.leader.login.protobuf.protocol.LoginProtocol.ResUserRegisterMessage;
import com.leader.login.user.UserManager;

import io.netty.channel.Channel;

/***
 * 注册用户请求
 * 
 * @author siestacat
 *
 */
@Controller
@Protocol("LoginProtocol")
public class ReqUserRegisterHandler implements Handler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		try {
			ReqUserRegisterMessage message = (ReqUserRegisterMessage) m;

			JSONObject object = new JSONObject();
			String username = message.getUsername();
			String password = message.getPassword();
			int result = UserManager.getInstance().register(username, password, object);
			object.put("result", result);

			ResUserRegisterMessage.Builder response = ResUserRegisterMessage.newBuilder();
			response.setData(object.toJSONString());
			channel.writeAndFlush(response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {

		}
	}

}
