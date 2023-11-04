package com.example.polichat.websockets;

import com.example.polichat.message.MessageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private MessageService messageService;
    private BroadcastService broadcastService;

    @Autowired
    public SocketHandler(MessageService messageService, BroadcastService broadcastService) {
        this.messageService = messageService;
        this.broadcastService = broadcastService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setBroadcastService(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        broadcastService.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcastService.removeSession(session);
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        try {
            String payload = message.getPayload();
            JSONObject json = new JSONObject(payload);
            String senderUsername = json.getString("sender");
            String receiverUsername = json.getString("receiver");
            String content = json.getString("content");

            messageService.sendMessage(senderUsername, receiverUsername, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
