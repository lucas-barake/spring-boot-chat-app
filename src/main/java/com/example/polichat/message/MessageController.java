package com.example.polichat.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public Message sendMessage(@RequestParam String senderUsername,
                               @RequestParam String receiverUsername,
                               @RequestParam String content) {
        return messageService.sendMessage(senderUsername, receiverUsername, content);
    }

    @GetMapping("/messages")
    public List<Message> getConversation(@RequestParam Long user1Id,
                                         @RequestParam Long user2Id) {
        return messageService.getConversation(user1Id, user2Id);
    }
}