package com.blog.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String email;
    private String role;
    private String token;
    private String profileImageUrl;
}
