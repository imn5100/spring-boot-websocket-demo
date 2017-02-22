package com.shaw.websocket.assembly;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Set;

/**
 * Created by shaw on 2017/2/14 0014.
 */
@Component
public class MessageWebSocketHandler extends AbstractWebSocketHandler {

    //写时复制，保证现场安全。set结构保证同一个会话只存储一份
    Set<WebSocketSession> sessionList = new java.util.concurrent.CopyOnWriteArraySet<>();
//    @Autowired
//    ParticipantRepository participantRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessionList.add(webSocketSession);
        System.out.println("afterConnectionEstablished");
    }


    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage webSocketMessage) throws Exception {
        TextMessage myMessage = new TextMessage("I:" + webSocketMessage.getPayload());
        TextMessage otherMessage = new TextMessage(webSocketSession.getId() + ":" + webSocketMessage.getPayload());
        for (WebSocketSession session : sessionList) {
            if (session != null && webSocketSession != session) {
                session.sendMessage(otherMessage);
            }
        }
        webSocketSession.sendMessage(myMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("afterConnectionClosed");
        sessionList.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
