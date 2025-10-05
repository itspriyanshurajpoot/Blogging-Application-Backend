package com.blog.utils.mappers;


import com.blog.dtos.category.CategoryRequest;
import com.blog.dtos.category.CategoryResponse;
import com.blog.entities.Category;

public class CategoryMapper {

    // toDTO
    public static CategoryResponse toDTO(Category category){
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    // toEntity
    public static Category toEntity(CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }
}
