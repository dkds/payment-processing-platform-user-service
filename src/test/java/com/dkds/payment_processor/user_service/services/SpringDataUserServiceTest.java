package com.dkds.payment_processor.user_service.services;

import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.repositories.UserRepository;
import com.dkds.payment_processor.user_service.services.impl.SpringDataUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpringDataUserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    SpringDataUserService userService;

    @Test
    void shouldReturnUserByEmailWhenFound() {
        // Arrange
        String testEmail = "test@test.com";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail(testEmail);
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> userOptional = userService.findUserByEmail(testEmail);

        // Assert
        Assertions.assertTrue(userOptional.isPresent());
        verify(userRepository, times(1)).findByEmail(testEmail);
    }

    @Test
    void shouldReturnEmptyUserByEmailWhenNotFound() {
        // Arrange
        String testEmail = "test1@test.com";
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        // Act
        Optional<User> userOptional = userService.findUserByEmail(testEmail);

        // Assert
        Assertions.assertTrue(userOptional.isEmpty());
        verify(userRepository, times(1)).findByEmail(testEmail);
    }

}
