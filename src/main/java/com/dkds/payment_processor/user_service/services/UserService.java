package com.dkds.payment_processor.user_service.services;

import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Scope("prototype")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        log.info("Bean is initialized");
    }

    @PreDestroy
    public void destroy() {
        log.info("Bean is about to be destroyed");
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateWalletBalance(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setWalletBalance(user.getWalletBalance() + amount);
        userRepository.save(user);
    }
}
