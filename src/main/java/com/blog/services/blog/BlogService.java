package com.blog.services.blog;

import com.blog.dtos.blogs.BlogRequestDTO;
import com.blog.dtos.blogs.BlogResponseDTO;
import com.blog.dtos.blogs.PaginatedBlogResponseDTO;
import com.blog.entities.Blog;
import com.blog.entities.Category;
import com.blog.entities.User;
import com.blog.repositories.BlogRepository;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.cloudinary.CloudinaryService;
import com.blog.utils.exceptions.ResourceNotFoundException;
import com.blog.utils.mappers.BlogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BlogService implements IBlogService{


    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    @Override
    public BlogResponseDTO addBlog(BlogRequestDTO dto, Principal principal) throws IOException {
        // Check the user exists or not
        User user = userRepository.findByEmailIgnoreCase(principal.getName());
        if(user == null){
            throw new ResourceNotFoundException("User", "email", principal.getName());
        }


        // Check the category is exists or not, if exist then assign it otherwise create a new category
        Category category = categoryRepository.findByNameIgnoreCase(dto.getCategory())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(dto.getCategory());
                    return categoryRepository.save(newCategory);
                });


        // Upload the blog's image to the cloudinary
        String imageUrl = cloudinaryService.uploadImage(dto.getImage());


        // Convert the requestDTO to Entity and set the user, category and profile to the user
        Blog blog = BlogMapper.toEntity(dto);
        blog.setCategory(category);
        blog.setUser(user);
        blog.setImageUrl(imageUrl);


        // Save the post and return a response DTO
        return BlogMapper.toDTO(blogRepository.save(blog));
    }

    @Override
    public BlogResponseDTO getBlogById(String id) {
        return blogRepository.findById(id)
                .map(BlogMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
    }

    @Override
    public void deleteBlog(String id, Principal principal) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        User user = userRepository.findByEmailIgnoreCase(principal.getName());

        if(!user.getId().equals(blog.getUser().getId()) || !user.getRole().toString().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorised");
        }

        blogRepository.delete(blog);
    }

    @Override
    public BlogResponseDTO updateBlog(String blogId, BlogRequestDTO dto, Principal principal) throws IOException {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        User user = userRepository.findByEmailIgnoreCase(principal.getName());

        if(user == null){
            throw new ResourceNotFoundException("User", "email", principal.getName());
        }

        if(user.getId().equals(blog.getUser().getId())){
            throw new RuntimeException("Unauthorised");
        }

        // Check the category is exists or not, if exist then assign it otherwise create a new category
        Category category = categoryRepository.findByNameIgnoreCase(dto.getCategory())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(dto.getCategory());
                    return categoryRepository.save(newCategory);
                });


        // Upload the blog image to the cloudinary and store the imageUrl
        String imageUrl = cloudinaryService.uploadImage(dto.getImage());

        // Update all the value of the blog
        blog.setTitle(dto.getTitle());
        blog.setContent(dto.getContent());
        blog.setImageUrl(imageUrl);
        blog.setCategory(category);
        return BlogMapper.toDTO(blogRepository.save(blog));
    }

    @Override
    public PaginatedBlogResponseDTO getAllBlog(int offset, int pageSize) {
        return BlogMapper.toPaginatedDTO(blogRepository.findAll(PageRequest.of(offset, pageSize)));


    }

    @Override
    public PaginatedBlogResponseDTO getAllBlogByCategory(String categoryName, int offset, int pageSize) {
        // Check the category exists or not
        Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));


        // If exists then fetch all the blogs of that category and convert it to DTO and return
        return BlogMapper.toPaginatedDTO(blogRepository.findByCategory(category, PageRequest.of(offset, pageSize)));
    }

    @Override
    public List<BlogResponseDTO> getAllBlogByUser(Principal principal) {
        // Check the user exists or not
        User user = userRepository.findByEmailIgnoreCase(principal.getName());

        if(user == null){
            throw new ResourceNotFoundException("User", "email", principal.getName());
        }

        return blogRepository.findByUser(user)
                .stream()
                .map(BlogMapper::toDTO)
                .toList();
    }

    @Override
    public List<BlogResponseDTO> getAllBlogByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));


        return blogRepository.findByUser(user)
                .stream()
                .map(BlogMapper::toDTO)
                .toList();
    }
}
