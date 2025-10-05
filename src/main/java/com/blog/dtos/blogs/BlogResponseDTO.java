package com.blog.dtos.blogs;

import com.blog.dtos.comment.CommentResponseDTO;
import com.blog.dtos.user.UserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponseDTO {
    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private String category;
    private UserResponseDTO user;
    private List<CommentResponseDTO> comments;
    private LocalDateTime createdAt;
}
