package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.dto.UserDto;
import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/kyc")
    public ResponseEntity<UserDto> createKyc(@Valid @RequestBody UserDto request) {
        User user = modelMapper.map(request, User.class);
        User savedUser = userService.createKyc(user);
        UserDto response = modelMapper.map(savedUser, UserDto.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/kyc")
    public ResponseEntity<UserDto> getKycById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getKycById(id));
    }

    @PutMapping("/{id}/kyc")
    public ResponseEntity<UserDto> updateKyc(@PathVariable Long id, @Valid @RequestBody UserDto request) {
        return ResponseEntity.ok(userService.updateKyc(id, request));
    }

    @PostMapping("/{id}/kyc/documents")
    public ResponseEntity<UserDto> uploadKyc(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.uploadKycDocument(id, file));
    }

    @PostMapping("/{id}/kyc/verify")
    public ResponseEntity<UserDto> verifyKyc(@PathVariable Long id, @RequestBody KycVerificationRequest request) {
        return ResponseEntity.ok(userService.verifyKycDocument(id, request));
    }
}
