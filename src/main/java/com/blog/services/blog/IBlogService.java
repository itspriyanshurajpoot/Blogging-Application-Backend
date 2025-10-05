package com.blog.services.blog;

import com.blog.dtos.blogs.BlogRequestDTO;
import com.blog.dtos.blogs.BlogResponseDTO;
import com.blog.dtos.blogs.PaginatedBlogResponseDTO;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface IBlogService {

    // Add Blog
    BlogResponseDTO addBlog(BlogRequestDTO dto, Principal principal) throws IOException;
    BlogResponseDTO getBlogById(String id);
    void deleteBlog(String id, Principal principal);

    BlogResponseDTO updateBlog(String blogId, BlogRequestDTO dto, Principal principal) throws IOException;

    PaginatedBlogResponseDTO getAllBlog(int offset, int pageSize);

    PaginatedBlogResponseDTO getAllBlogByCategory(String categoryName, int offset, int pageSize);

    List<BlogResponseDTO> getAllBlogByUser(Principal principal);

    List<BlogResponseDTO> getAllBlogByUserId(String userId);
}
