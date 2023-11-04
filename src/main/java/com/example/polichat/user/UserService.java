package com.example.polichat.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    public User createOrFindUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername(username);
                    User savedUser = userRepository.save(user);

                    eventPublisher.publishEvent(new UserCreatedEvent(this, savedUser));

                    return savedUser;
                });
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}