package com.dkds.payment_processor.user_service.services.impl;

import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.repositories.UserRepository;
import com.dkds.payment_processor.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SpringDataUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createKyc(User request) {
        return userRepository.save(request);
    }

    @Override
    public User getKycById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
