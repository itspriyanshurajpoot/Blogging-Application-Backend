package com.blog.repositories;

import com.blog.entities.Blog;
import com.blog.entities.Category;
import com.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> findByUser(User user);
    Page<Blog> findByCategory(Category category, Pageable pageable);

    // Search blog by title or content in a paginated way
    Page<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
}
