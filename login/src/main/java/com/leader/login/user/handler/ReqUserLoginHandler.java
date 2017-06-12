package com.leader.login.user.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqUserLoginMessage;
import com.leader.login.protobuf.protocol.LoginProtocol.ResUserLoginMessage;
import com.leader.login.user.UserManager;

import io.netty.channel.Channel;

/***
 * 用户登录请求
 * 
 * @author siestacat
 *
 */
@Controller
@Protocol("LoginProtocol")
public class ReqUserLoginHandler implements Handler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void action(Channel channel, Message m) {
		try {
			ReqUserLoginMessage message = (ReqUserLoginMessage) m;

			JSONObject object = new JSONObject();
			String username = message.getUsername();
			String password = message.getPassword();
			int result = UserManager.getInstance().login(username, password, object);
			object.put("result", result);

			ResUserLoginMessage.Builder response = ResUserLoginMessage.newBuilder();
			response.setData(object.toJSONString());
			channel.writeAndFlush(response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
