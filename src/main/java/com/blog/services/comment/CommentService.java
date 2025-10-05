package com.blog.services.comment;

import com.blog.dtos.comment.CommentRequestDTO;
import com.blog.dtos.comment.CommentResponseDTO;
import com.blog.entities.Blog;
import com.blog.entities.Comment;
import com.blog.entities.User;
import com.blog.repositories.BlogRepository;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.UserRepository;
import com.blog.utils.exceptions.ResourceNotFoundException;
import com.blog.utils.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService{

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    @Override
    public CommentResponseDTO addComment(CommentRequestDTO dto, Principal principal) {
        // Find out the user from principal
        User user = userRepository.findByEmailIgnoreCase(principal.getName());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Find out the blog by blogId
        Blog blog = blogRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", dto.getBlogId()));


        // Create Comment entity and set the values from dto
        Comment comment = CommentMapper.toEntity(dto);
        comment.setBlog(blog);
        comment.setUser(user);


        // Save the comment entity to the database
        Comment savedComment = commentRepository.save(comment);

        // Convert the saved comment entity to CommentResponseDTO and return
        return CommentMapper.toDTO(savedComment);
    }

    @Override
    public void deleteComment(String commentId, Principal principal) {
        // Find out the user from principal
        User user = userRepository.findByEmailIgnoreCase(principal.getName());

        if (user == null) {
            throw new ResourceNotFoundException("User", "email", principal.getName());
        }

        // Find out the comment by commentId
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        // Check if the user is the owner of the comment or has admin role
        // If yes, delete the comment
        // If no, throw an exception

        if(!comment.getUser().getId().equalsIgnoreCase(user.getId()) || !user.getRole().toString().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("You are not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }
}
