package com.blog.controllers;

import com.blog.dtos.user.LoginRequestDTO;
import com.blog.dtos.user.RegistrationRequestDTO;
import com.blog.responses.SuccessResponse;
import com.blog.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IUserService userService;

    // Register user
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser(@Valid @ModelAttribute RegistrationRequestDTO requestDTO) throws IOException {
        SuccessResponse response = SuccessResponse.builder()
                .message("User registered successfully")
                .success(true)
                .data(userService.registerUser(requestDTO))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> loginUser(@Valid @RequestBody LoginRequestDTO requestDTO){
        SuccessResponse response = SuccessResponse.builder()
                .success(true)
                .message("Login successful")
                .data(userService.loginUser(requestDTO))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
