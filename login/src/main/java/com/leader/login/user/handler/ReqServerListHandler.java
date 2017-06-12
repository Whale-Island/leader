// package com.leader.login.user.handler;
//
// import static io.netty.handler.codec.http.HttpResponseStatus.OK;
// import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
//
// import java.util.Map;
// import java.util.Map.Entry;
// import java.util.Set;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Controller;
//
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
// import com.leader.core.server.http.AbstractChannelHandler;
// import com.leader.core.server.model.Protocol;
// import com.leader.login.server.LoginServer;
// import com.leader.login.server.model.Server;
//
// import io.netty.channel.ChannelHandlerContext;
// import io.netty.handler.codec.http.FullHttpRequest;
//
/// ***
// * 请求服务器列表
// *
// * @author siestacat
// *
// */
// @Controller
// @Protocol("LoginProtocol")
// public class ReqServerListHandler extends AbstractChannelHandler {
//
// private Logger log = LoggerFactory.getLogger(getClass());
//
// @Override
// public void channel(ChannelHandlerContext ctx, FullHttpRequest req,
// Map<String, String> map) {
// JSONArray array = new JSONArray();
// try {
// Set<Entry<Integer, Server>> set =
// LoginServer.getInstance().getServers().entrySet();
// for (Entry<Integer, Server> entry : set) {
// Server server = entry.getValue();
// JSONObject object = new JSONObject();
// object.put("id", server.getServerId());
// object.put("name", server.getName());
// object.put("ip", server.getIp());
// object.put("port", server.getPort());
// object.put("state", server.getState());
// object.put("online", server.getOnline());
// object.put("isRecommend", server.isRecommend());
// array.add(object);
// }
// } catch (Exception e) {
// log.error(e.getMessage(), e);
// } finally {
// sendHttpResponse(ctx, req, HTTP_1_1, OK, array.toJSONString());
// }
// }
//
// }
