package com.blog.dtos.category;

import com.blog.dtos.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private String id;
    private UserResponseDTO user;
    private String name;
}
