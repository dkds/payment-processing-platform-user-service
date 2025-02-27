package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.dto.UserDto;
import com.dkds.payment_processor.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Create User KYC Data
    @PostMapping("/kyc")
    public ResponseEntity<UserDto> createKyc(@Valid @RequestBody UserDto request) {
        return ResponseEntity.ok(userService.createKyc(request));
    }

    // Get User KYC by ID
    @GetMapping("/{id}/kyc")
    public ResponseEntity<UserDto> getKycById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getKycById(id));
    }

    // Update User KYC Data
    @PutMapping("/{id}/kyc")
    public ResponseEntity<UserDto> updateKyc(@PathVariable Long id, @Valid @RequestBody UserDto request) {
        return ResponseEntity.ok(userService.updateKyc(id, request));
    }

    // Upload KYC Documents
    @PostMapping("/{id}/kyc/documents")
    public ResponseEntity<UserDto> uploadKyc(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.uploadKycDocument(id, file));
    }

    // Verify KYC Documents
    @PostMapping("/{id}/kyc/verify")
    public ResponseEntity<UserDto> verifyKyc(@PathVariable Long id, @RequestBody KycVerificationRequest request) {
        return ResponseEntity.ok(userService.verifyKycDocument(id, request));
    }
}
