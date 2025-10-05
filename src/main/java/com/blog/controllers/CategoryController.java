package com.blog.controllers;

import com.blog.dtos.category.CategoryRequest;
import com.blog.dtos.category.CategoryResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.responses.SuccessResponse;
import com.blog.services.category.ICategoryService;
import com.blog.utils.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ICategoryService categoryService;

    // Get All Categories
    @GetMapping("/public/get-all-category")
    public ResponseEntity<SuccessResponse> getAllCategories(){
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Categories fetched successfully")
                .data(categoryService.getAllCategory())
                .success(true)
                .build();

        return ResponseEntity.ok(successResponse);
    }

    // Create Category
    @PostMapping("/create-category")
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody CategoryRequest request){
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Create category endpoint")
                .data(categoryService.addCategory(request))
                .success(true)
                .build();

        return ResponseEntity.ok(successResponse);
    }

    // Delete Category
    @DeleteMapping("/delete-category/{categoryId}")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Category deleted successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(successResponse);
    }
}
