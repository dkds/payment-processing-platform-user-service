package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.config.JwtUtil;
import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(JwtUtil.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnUserByUsername() throws Exception {
        String testUsername = "test@test.com";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername(testUsername);
        when(userService.findUserByUsername(testUsername)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/api/users/" + testUsername).header("Authorization", "Bearer your-jwt-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testUsername));
    }
}
