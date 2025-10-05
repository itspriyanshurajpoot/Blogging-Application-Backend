package com.blog.services.user;

import com.blog.dtos.user.LoginRequestDTO;
import com.blog.dtos.user.RegistrationRequestDTO;
import com.blog.dtos.user.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


public interface IUserService {

    UserResponseDTO registerUser(RegistrationRequestDTO requestDTO) throws IOException;
    UserResponseDTO loginUser(LoginRequestDTO requestDTO);

    UserResponseDTO getUserById(String id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO getUserByToken(Principal principal);
    void deleteUserById(String id);

    UserResponseDTO updateUser(String id, RegistrationRequestDTO requestDTO);

    UserResponseDTO updateProfileImage(String id, MultipartFile file) throws IOException;

    void logoutUser(Principal principal);
}
