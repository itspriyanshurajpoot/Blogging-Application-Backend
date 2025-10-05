package com.blog.controllers;

import com.blog.dtos.user.RegistrationRequestDTO;
import com.blog.responses.SuccessResponse;
import com.blog.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<SuccessResponse> getUserById(@RequestParam String userId){
        SuccessResponse response = SuccessResponse.builder()
                .message("User fetched successfully")
                .success(true)
                .data(userService.getUserById(userId))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-token")
    public ResponseEntity<SuccessResponse> getUserByToken(Principal principal){
        SuccessResponse response = SuccessResponse.builder()
                .message("User fetched successfully")
                .success(true)
                .data(userService.getUserByToken(principal))
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteUserById(@PathVariable String id){
        userService.deleteUserById(id);
        SuccessResponse response = SuccessResponse.builder()
                .message("User deleted successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateUser(@PathVariable String id, @ModelAttribute RegistrationRequestDTO requestDTO){
        SuccessResponse response = SuccessResponse.builder()
                .message("User updated successfully")
                .success(true)
                .data(userService.updateUser(id, requestDTO))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/profile-image")
    public ResponseEntity<SuccessResponse> updateProfileImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        SuccessResponse response = SuccessResponse.builder()
                .message("Profile image updated successfully")
                .success(true)
                .data(userService.updateProfileImage(id, file))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<SuccessResponse> logoutUser(Principal principal){
        userService.logoutUser(principal);
        SuccessResponse response = SuccessResponse.builder()
                .message("Logout successful")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

}
