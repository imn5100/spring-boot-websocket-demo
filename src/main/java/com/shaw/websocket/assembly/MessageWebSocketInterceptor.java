package com.shaw.websocket.assembly;

import com.shaw.constants.Utils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 *
 * @author shaw
 * @date 2017/2/14 0014
 */
@Component
public class MessageWebSocketInterceptor extends HttpSessionHandshakeInterceptor {
    private static final String COOKIE_NAME = "WEB_SESSION_DATA";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            Map<String, Object> map = Utils.getParamsFromCookie(serverRequest.getServletRequest(), COOKIE_NAME);
            if (!CollectionUtils.isEmpty(map)) {
                for (Map.Entry entry : map.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception e) {
        super.afterHandshake(request, response, wsHandler, e);
    }

}
