package com.dkds.payment_processor.user_service.services.impl;

import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.repositories.UserRepository;
import com.dkds.payment_processor.user_service.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SpringDataUserService implements UserService {

    private final UserRepository userRepository;

    // constructor injection is more test friendly and shows the dependencies of the class more clearly
    @Autowired
    public SpringDataUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        log.info("Bean is initialized");
    }

    @PreDestroy
    public void destroy() {
        log.info("Bean is about to be destroyed");
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateWalletBalance(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setWalletBalance(user.getWalletBalance() + amount);
        userRepository.save(user);
    }
}
