package com.example.polichat.user;

import com.example.polichat.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserCache {

    private final List<User> users = new CopyOnWriteArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }
}