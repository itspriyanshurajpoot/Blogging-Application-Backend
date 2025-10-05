package com.blog.dtos.blogs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequestDTO {
    private String title;
    private String content;
    private MultipartFile image;
    private String category;
}
