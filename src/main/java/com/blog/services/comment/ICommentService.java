package com.blog.services.comment;

import com.blog.dtos.comment.CommentRequestDTO;
import com.blog.dtos.comment.CommentResponseDTO;

import java.security.Principal;

public interface ICommentService {

    // Add Comment
    CommentResponseDTO addComment(CommentRequestDTO dto, Principal principal);


    // Delete Comment
    void deleteComment(String commentId, Principal principal);
}
