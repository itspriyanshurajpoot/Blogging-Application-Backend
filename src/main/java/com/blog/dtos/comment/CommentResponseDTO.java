package com.blog.dtos.comment;

import com.blog.dtos.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private String id;
    private String content;
    private UserResponseDTO user;
}
