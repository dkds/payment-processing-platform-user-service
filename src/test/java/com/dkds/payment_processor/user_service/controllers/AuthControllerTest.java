package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.config.JwtUtil;
import com.dkds.payment_processor.user_service.config.SecurityConfig;
import com.dkds.payment_processor.user_service.dto.CustomUserDetails;
import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.services.impl.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void shouldReturnJwtTokenOnSuccessfulLogin() throws Exception {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        String token = "mock-jwt-token";

        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(List.of("USER"));

        when(userDetailsService.loadUserByUsername(username)).thenReturn(new CustomUserDetails(user));
        when(jwtUtil.generateToken(username)).thenReturn(token);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
