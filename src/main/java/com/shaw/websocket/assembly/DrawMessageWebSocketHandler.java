package com.shaw.websocket.assembly;

import com.alibaba.fastjson.JSON;
import com.shaw.model.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Set;

import static com.shaw.constants.BaseConstants.DRAW_TYPE;

/**
 * Created by shaw on 2017/2/23 0023.
 * 同步画图ws服务handler
 * 简单的事件传递handler，接收前端的画图事件，发送给其他链接了此服务的客户端
 */
@Component
public class DrawMessageWebSocketHandler extends AbstractWebSocketHandler {
    //写时复制，保证线程安全。set结构保证同一个会话只存储一份
    Set<WebSocketSession> sessionSet = new java.util.concurrent.CopyOnWriteArraySet<>();
    Logger logger = LoggerFactory.getLogger(DrawMessageWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessionSet.add(webSocketSession);
    }


    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage webSocketMessage) throws Exception {
        String msg = webSocketMessage.getPayload();
        if (StringUtils.isEmpty(msg)) {
            return;
        } else if (msg.startsWith("{")) {
            try {
                BaseMsg getMsg = JSON.parseObject(msg, BaseMsg.class);
                //画图事件消息。暂时不需要解析直接传递给其他客户端
                if (!StringUtils.isEmpty(getMsg.getType()) && getMsg.getType().startsWith(DRAW_TYPE)) {
                    for (WebSocketSession session : sessionSet) {
                        if (session != webSocketSession)
                            session.sendMessage(webSocketMessage);
                    }
                    return;
                }
            } catch (Exception e) {
                logger.error("handleDrawMessage error", e);
                webSocketSession.close();
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        sessionSet.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        sessionSet.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
