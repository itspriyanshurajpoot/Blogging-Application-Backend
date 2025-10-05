package com.blog.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequestDTO {

    @NotEmpty(message = "Email is required")
    @Email(message = "A valid email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;
}
