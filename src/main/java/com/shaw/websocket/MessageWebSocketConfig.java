package com.shaw.websocket;

import com.shaw.websocket.assembly.DrawMessageWebSocketHandler;
import com.shaw.websocket.assembly.MessageWebSocketHandler;
import com.shaw.websocket.assembly.MessageWebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by shaw on 2017/2/14 0014.
 */
@Configuration
@EnableWebSocket
public class MessageWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MessageWebSocketHandler messageWebSocketHandler;
    @Autowired
    private MessageWebSocketInterceptor messageWebSocketInterceptor;
    @Autowired
    private DrawMessageWebSocketHandler drawMessageWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //无法兼容webSocket的浏览器可以使用sockJS，为轮询模式。  连接已http开头，为轮询模式
        registry.addHandler(messageWebSocketHandler, "/ws/chat").addInterceptors(messageWebSocketInterceptor).setAllowedOrigins("*");//.withSockJS();
        registry.addHandler(drawMessageWebSocketHandler, "/ws/draw").addInterceptors(messageWebSocketInterceptor).setAllowedOrigins("*");//.withSockJS();
    }
}
