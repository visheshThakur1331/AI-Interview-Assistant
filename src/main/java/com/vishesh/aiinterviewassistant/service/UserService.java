package com.vishesh.aiinterviewassistant.service;

import com.vishesh.aiinterviewassistant.entity.User;
import com.vishesh.aiinterviewassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
    public java.util.List<User> getLatestUsers() {

        return userRepository.findTop10ByOrderByIdDesc();
    }
}