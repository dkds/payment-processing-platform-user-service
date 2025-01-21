package com.dkds.payment_processor.user_service.services;

import com.dkds.payment_processor.user_service.entities.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    void updateWalletBalance(Long userId, Double amount);
}
