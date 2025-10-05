package com.blog.services.user;

import com.blog.dtos.user.LoginRequestDTO;
import com.blog.dtos.user.RegistrationRequestDTO;
import com.blog.dtos.user.UserResponseDTO;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.repositories.UserRepository;
import com.blog.services.cloudinary.ICloudinaryService;
import com.blog.services.token.ITokenService;
import com.blog.utils.exceptions.ResourceAlreadyExistsException;
import com.blog.utils.exceptions.ResourceNotFoundException;
import com.blog.utils.jwt.JwtUtils;
import com.blog.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICloudinaryService cloudinaryService;
    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;

    @Override
    public UserResponseDTO registerUser(RegistrationRequestDTO requestDTO) throws IOException {
        User user = userRepository.findByEmailIgnoreCase(requestDTO.getEmail());


        if(user != null) {
            throw new ResourceAlreadyExistsException("User", "email", requestDTO.getEmail());
        }

        String profileUrl = cloudinaryService.uploadProfile(requestDTO.getFile());


        user = UserMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setProfileImageUrl(profileUrl);
        user.setRole(Role.USER);
        User newUser = userRepository.save(user);

        // Generate token (implementation not shown here)
        String token = jwtUtils.generateToken(newUser.getEmail());


        // Save the token
        tokenService.addToken(token, newUser);

        return UserMapper.toResponseWithToken(newUser, token);
    }

    @Override
    public UserResponseDTO loginUser(LoginRequestDTO requestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new RuntimeException("Invalid email or password");
        }

        // Double check
        User user = userRepository.findByEmailIgnoreCase(requestDTO.getEmail());

        // Generate token
        String token = jwtUtils.generateToken(user.getEmail());

        // Update the token
        tokenService.updateToken(token, user);
        return UserMapper.toResponseWithToken(user, token);
    }

    @Override
    public UserResponseDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO getUserByToken(Principal principal) {
        User user = userRepository.findByEmailIgnoreCase(principal.getName());
        return UserMapper.toResponse(user);
    }

    @Override
    public void deleteUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO updateUser(String id, RegistrationRequestDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if(!user.getEmail().equalsIgnoreCase(requestDTO.getEmail())){
            if(userRepository.findByEmailIgnoreCase(requestDTO.getEmail()) != null){
                throw new ResourceAlreadyExistsException("User", "email", requestDTO.getEmail());
            }
            user.setEmail(requestDTO.getEmail());
        }
        user.setFullName(requestDTO.getFullName());
        User updatedUser = userRepository.save(user);
        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public UserResponseDTO updateProfileImage(String id, MultipartFile file) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        String profileUrl = cloudinaryService.uploadProfile(file);
        user.setProfileImageUrl(profileUrl);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void logoutUser(Principal principal) {
        // Find user by email which is coming from token
        User user = userRepository.findByEmailIgnoreCase(principal.getName());

        // Find token by user and set isLogout field as false and save it
        tokenService.setIsLogout(user);
    }

}
