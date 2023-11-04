package com.example.polichat.websockets;

import com.example.polichat.user.UserCache;
import com.example.polichat.message.Message;
import com.example.polichat.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BroadcastService {
    @Autowired
    private UserCache userCache;
    @Autowired
    private ObjectMapper objectMapper;

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void broadcastNewMessage(Message message) throws IOException {
        String messageJson = objectMapper.writeValueAsString(message);
        TextMessage textMessage = new TextMessage(messageJson);

        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }

    /**
     * Difunde la lista actual de usuarios a todos los clientes conectados a través de WebSocket.
     * La lista de usuarios se obtiene de UserCache y se serializa a una cadena JSON,
     * que se envía como un mensaje de texto a través de cada conexión WebSocket.
     *
     * @throws IOException si se produce un error de I/O al enviar el mensaje
     */
    public void broadcastUserListUpdate() throws IOException {
        List<User> users = userCache.getUsers();
        String userUpdateMessage = new ObjectMapper().writeValueAsString(users);
        TextMessage textMessage = new TextMessage(userUpdateMessage);

        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }
}
