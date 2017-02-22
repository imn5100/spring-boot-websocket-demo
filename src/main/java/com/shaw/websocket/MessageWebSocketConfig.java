package com.shaw.websocket;

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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocketHandler, "/websocket").addInterceptors(messageWebSocketInterceptor).setAllowedOrigins("*").withSockJS();
    }
}
