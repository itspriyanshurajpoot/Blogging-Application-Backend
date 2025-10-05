package com.blog.dtos.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

    @NotEmpty(message = "name cannot be empty")
    private String fullName;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email should be valid")
    private String email;

    @NotEmpty(message = "password cannot be empty")
    private String password;

//    @NotEmpty(message = "Profile image is required")
    private MultipartFile file;
}
