package com.blog.services.category;

import com.blog.dtos.category.CategoryRequest;
import com.blog.dtos.category.CategoryResponse;
import com.blog.entities.Category;
import com.blog.repositories.CategoryRepository;
import com.blog.utils.exceptions.ResourceAlreadyExistsException;
import com.blog.utils.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {
        // Check the category name is not taken
        Category category = categoryRepository.findByNameIgnoreCase(request.getName())
                .orElse(null);

        if(category != null){
            throw new ResourceAlreadyExistsException("Category", "name", request.getName());
        }

        // Save the category
        category = CategoryMapper.toEntity(request);;
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {
                            throw new ResourceAlreadyExistsException("Category", "id", categoryId);
                        });
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }


}
