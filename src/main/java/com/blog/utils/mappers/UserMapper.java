package com.blog.utils.mappers;

import com.blog.dtos.user.RegistrationRequestDTO;
import com.blog.dtos.user.UserResponseDTO;
import com.blog.entities.User;

public class UserMapper {

    public static UserResponseDTO toResponseWithToken(User user, String token) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .token(token)
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    public static User toEntity(RegistrationRequestDTO requestDTO) {
        User user = new User();
        user.setFullName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmail());
        return user;
    }
}
