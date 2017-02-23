package com.shaw.websocket.assembly;

import com.alibaba.fastjson.JSON;
import com.shaw.model.BaseMsg;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.Set;

import static com.shaw.constants.BaseConstants.*;

/**
 * Created by shaw on 2017/2/14 0014.
 */
@Component
public class MessageWebSocketHandler extends AbstractWebSocketHandler {

    //写时复制，保证线程安全。set结构保证同一个会话只存储一份
    Set<WebSocketSession> sessionSet = new java.util.concurrent.CopyOnWriteArraySet<>();
    //映射 id和 用户名（用户信息）。实际应用应该是登录时验证用户信息，将用户信息缓存于此。这里直接接收了用户传递的name，页面显示的用户信息。
    Map<String, String> idNameMap = new java.util.concurrent.ConcurrentHashMap<String, String>();

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
                if (StringUtils.isEmpty(getMsg.getType()) || StringUtils.isEmpty(getMsg.getContents())) {
                    return;
                } else {
                    if (getMsg.getType().equalsIgnoreCase(LOGIN_TYPE)) {
                        sessionSet.add(webSocketSession);
                        idNameMap.put(webSocketSession.getId(), getMsg.getContents());
                        notifyOnlineOrOffline(getMsg.getContents(), true);
                    } else if (getMsg.getType().equalsIgnoreCase(SEND_MSG_TYPE)) {
                        String myName = idNameMap.get(webSocketSession.getId());
                        BaseMsg sendMsg = new BaseMsg();
                        sendMsg.setType(SEND_MSG_TYPE);
                        for (WebSocketSession session : sessionSet) {
                            sendMsg.setContents(myName + ":" + getMsg.getContents());
                            session.sendMessage(new TextMessage(JSON.toJSONString(sendMsg)));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                webSocketSession.close();
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        sessionSet.remove(webSocketSession);
        String name = idNameMap.remove(webSocketSession.getId());
        if (name != null)
            notifyOnlineOrOffline(name, false);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        sessionSet.remove(webSocketSession);
        String name = idNameMap.remove(webSocketSession.getId());
        if (name != null)
            notifyOnlineOrOffline(name, false);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void notifyOnlineOrOffline(String onlineUser, boolean isOnline) throws Exception {
        int userNum = sessionSet.size();
        BaseMsg countSendMsg = new BaseMsg();
        countSendMsg.setType(COUNT_TYPE);
        countSendMsg.setContents(String.valueOf(userNum));
        BaseMsg sendMsg = new BaseMsg();
        sendMsg.setType(SEND_MSG_TYPE);
        sendMsg.setContents(onlineUser + (isOnline ? " is Online!" : " is Offline"));
        String countSendMsgStr = JSON.toJSONString(countSendMsg);
        String sendMsgStr = JSON.toJSONString(sendMsg);
        TextMessage sendTextMessage = new TextMessage(sendMsgStr);
        TextMessage countSendTextMessage = new TextMessage(countSendMsgStr);
        for (WebSocketSession session : sessionSet) {
            session.sendMessage(countSendTextMessage);
            session.sendMessage(sendTextMessage);
        }
    }
}
