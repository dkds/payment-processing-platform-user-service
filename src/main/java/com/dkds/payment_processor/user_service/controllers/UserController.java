package com.dkds.payment_processor.user_service.controllers;

import com.dkds.payment_processor.user_service.dto.FileDto;
import com.dkds.payment_processor.user_service.dto.UserDto;
import com.dkds.payment_processor.user_service.entities.User;
import com.dkds.payment_processor.user_service.services.StorageService;
import com.dkds.payment_processor.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final StorageService storageService;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authenticated user: {}", authentication.getName());
        User user = userService.getKycById(id);
        UserDto response = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/kyc")
    public ResponseEntity<UserDto> updateKyc(@PathVariable Long id, @Valid @RequestBody UserDto request) {
        User user = modelMapper.map(request, User.class);
        User savedUser = userService.updateKyc(id, user);
        UserDto response = modelMapper.map(savedUser, UserDto.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/kyc/documents")
    public ResponseEntity<UserDto> uploadKyc(@PathVariable Long id, @RequestParam("files") MultipartFile[] files) {
        List<FileDto> storedDetails = storageService.store(files);
        userService.saveDocuments(id, storedDetails);
        return ResponseEntity.accepted().build();
    }
}
