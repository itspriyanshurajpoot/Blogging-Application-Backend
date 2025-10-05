package com.blog.utils.mappers;

import com.blog.dtos.blogs.BlogRequestDTO;
import com.blog.dtos.blogs.BlogResponseDTO;
import com.blog.dtos.blogs.PaginatedBlogResponseDTO;
import com.blog.entities.Blog;
import org.springframework.data.domain.Page;

public class BlogMapper {

    public static Blog toEntity(BlogRequestDTO dto){
        Blog blog = new Blog();
        blog.setTitle(dto.getTitle());
        blog.setContent(dto.getContent());
        return blog;
    }

    public static BlogResponseDTO toDTO(Blog blog){
        BlogResponseDTO dto = new BlogResponseDTO();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setContent(blog.getContent());
        dto.setCategory(blog.getCategory().getName());
        dto.setImageUrl(blog.getImageUrl());
        dto.setUser(UserMapper.toResponse(blog.getUser()));
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setComments(blog.getComments() != null ? blog.getComments().stream().map(CommentMapper::toDTO).toList() : null);
        return dto;
    }

    public static PaginatedBlogResponseDTO toPaginatedDTO(Page<Blog> blog){
        PaginatedBlogResponseDTO dto = new PaginatedBlogResponseDTO();
        dto.setBlogs(blog.getContent().stream().map(BlogMapper::toDTO).toList());
        dto.setPageNumber(blog.getNumber());
        dto.setPageSize(blog.getSize());
        dto.setTotalElements(blog.getTotalElements());
        dto.setTotalPages(blog.getTotalPages());
        dto.setFirst(blog.isFirst());
        dto.setLast(blog.isLast());
        dto.setNumberOfElements(blog.getNumberOfElements());
        dto.setEmpty(blog.isEmpty());
        return dto;
    }
}
