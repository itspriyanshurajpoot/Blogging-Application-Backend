package com.blog.services.category;

import com.blog.dtos.category.CategoryRequest;
import com.blog.dtos.category.CategoryResponse;

import java.util.List;

public interface ICategoryService {

    // Add Category
    CategoryResponse addCategory(CategoryRequest request);

    // Delete Category
    void deleteCategory(String categoryId);

    List<CategoryResponse> getAllCategory();
}
