package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.config.JwtUtil;
import com.dkds.payment_processor.user_service.dto.AuthRequest;
import com.dkds.payment_processor.user_service.services.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }
}
