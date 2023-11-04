package com.example.polichat.user;

import com.example.polichat.websockets.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserUpdateListener {
    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCache userCache;

    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        try {
            List<User> users = userRepository.findAll();
            userCache.setUsers(users);
            broadcastService.broadcastUserListUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
