package com.blog.controllers;

import com.blog.dtos.blogs.BlogRequestDTO;
import com.blog.dtos.blogs.BlogResponseDTO;
import com.blog.responses.SuccessResponse;
import com.blog.services.blog.IBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final IBlogService blogService;

    @PostMapping
    public ResponseEntity<SuccessResponse> addBlog(@ModelAttribute BlogRequestDTO dto, Principal principal) throws IOException {
        BlogResponseDTO blogResponseDTO = blogService.addBlog(dto, principal);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Blog added successfully")
                .success(true)
                .data(blogResponseDTO)
                .build();
        return ResponseEntity.status(201).body(successResponse);
    }

    // Get Blog by id
    @GetMapping("/public/{id}")
    public ResponseEntity<SuccessResponse> getBlogById(@PathVariable String id) {
        BlogResponseDTO blogResponseDTO = blogService.getBlogById(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Blog fetched successfully")
                .data(blogResponseDTO)
                .success(true)
                .build();
        return ResponseEntity.ok(successResponse);
    }


    // Delete Blog by id
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteBlog(@PathVariable String id, Principal principal) {
        blogService.deleteBlog(id, principal);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Blog deleted successfully")
                .success(true)
                .build();
        return ResponseEntity.ok(successResponse);
    }



    // Update Blog by id
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateBlog(@PathVariable String id, @ModelAttribute BlogRequestDTO dto, Principal principal) throws IOException {
        BlogResponseDTO blogResponseDTO = blogService.updateBlog(id, dto, principal);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Blog updated successfully")
                .success(true)
                .data(blogResponseDTO)
                .build();
        return ResponseEntity.ok(successResponse);
    }



    // Get All Blogs
    @GetMapping("/public")
    public ResponseEntity<SuccessResponse> getAllBlogs(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "6") int pageSize) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Blogs fetched successfully")
                .success(true)
                .data(blogService.getAllBlog(offset, pageSize))
                .build());
    }



    // Get All Blogs by User
    @GetMapping("/by-user")
    public ResponseEntity<SuccessResponse> getAllBlogsByUser(Principal principal) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Blogs fetched successfully")
                .success(true)
                .data(blogService.getAllBlogByUser(principal))
                .build());
    }

    // Get All Blogs by UserId
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<SuccessResponse> getAllBlogsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Blogs fetched successfully")
                .success(true)
                .data(blogService.getAllBlogByUserId(userId))
                .build());
    }

    // Get All Blogs by Category
    @GetMapping("/public/by-category/{categoryName}")
    public ResponseEntity<SuccessResponse> getAllBlogsByCategory(@PathVariable String categoryName,
                                                                 @RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "6") int pageSize) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Blogs fetched successfully")
                .success(true)
                .data(blogService.getAllBlogByCategory(categoryName, offset, pageSize))
                .build());
    }
}
