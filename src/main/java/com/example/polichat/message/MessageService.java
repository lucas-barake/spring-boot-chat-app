package com.example.polichat.message;

import com.example.polichat.user.User;
import com.example.polichat.user.UserRepository;
import com.example.polichat.websockets.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BroadcastService broadcastService;

    public Message sendMessage(String senderUsername, String receiverUsername, String content) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow();
        User receiver = userRepository.findByUsername(receiverUsername).orElseThrow();
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        try {
            broadcastService.broadcastNewMessage(savedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedMessage;
    }

    public List<Message> getConversation(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id).orElseThrow();
        User user2 = userRepository.findById(user2Id).orElseThrow();
        return messageRepository.findConversation(user1, user2);
    }
}
