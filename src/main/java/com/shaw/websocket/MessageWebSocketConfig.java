package com.shaw.websocket;

import com.shaw.websocket.assembly.DrawMessageWebSocketHandler;
import com.shaw.websocket.assembly.ChatMessageWebSocketHandler;
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
    private ChatMessageWebSocketHandler messageWebSocketHandler;
    @Autowired
    private DrawMessageWebSocketHandler drawMessageWebSocketHandler;
    @Autowired
    private MessageWebSocketInterceptor messageWebSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //无法兼容webSocket的浏览器可以使用sockJS，为轮询模式，连接为http开头。方法最后需要执行一下withSockJS()。轮询模式效率会低很多。
        //构建第一个聊天服务入口
        registry.addHandler(messageWebSocketHandler, "/ws/chat").addInterceptors(messageWebSocketInterceptor).setAllowedOrigins("*");//.withSockJS();
        //构建第二个绘图服务入口
        registry.addHandler(drawMessageWebSocketHandler, "/ws/draw").addInterceptors(messageWebSocketInterceptor).setAllowedOrigins("*");//.withSockJS();
    }
}
