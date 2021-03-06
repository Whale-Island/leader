package com.leader.login.user.handler;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.leader.core.server.http.AbstractChannelHandler;
import com.leader.core.server.model.Protocol;
import com.leader.login.user.UserManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/***
 * 用户登录请求
 * 
 * @author siestacat
 *
 */
@Controller
@Protocol("LoginProtocol")
public class ReqUserLoginHandler extends AbstractChannelHandler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void channel(ChannelHandlerContext ctx, FullHttpRequest req, Map<String, String> map) {
		JSONObject object = new JSONObject();
		try {
			String username = map.get("UsERnaME");
			String password = map.get("pASsWOrD");
			UserManager.getInstance().login(username, password, object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			sendHttpResponse(ctx, req, HTTP_1_1, OK, object.toJSONString());
		}
	}

}
