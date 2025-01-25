package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.config.JwtUtil;
import com.dkds.payment_processor.user_service.config.SecurityConfig;
import com.dkds.payment_processor.user_service.dto.CustomUserDetails;
import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.services.UserService;
import com.dkds.payment_processor.user_service.services.impl.CustomUserDetailsService;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void shouldReturnUserByUsername() throws Exception {
        var authData = setupAuth();

        String testUsername = "test@test.com";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername(testUsername);
        when(userService.findUserByUsername(testUsername)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/api/users/" + testUsername)
                        .header("Authorization", "Bearer " + authData.getLeft()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testUsername));
    }

    private Triple<String, User, String> setupAuth() {
        String username = "testuser";
        String password = "testpassword";
        String token = jwtUtil.generateToken(username);

        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(List.of("USER"));

        when(userDetailsService.loadUserByUsername(username)).thenReturn(new CustomUserDetails(user));

        return ImmutableTriple.of(token, user, password);
    }
}
