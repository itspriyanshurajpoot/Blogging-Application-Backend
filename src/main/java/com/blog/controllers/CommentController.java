package com.blog.controllers;


import com.blog.dtos.comment.CommentRequestDTO;
import com.blog.dtos.comment.CommentResponseDTO;
import com.blog.responses.SuccessResponse;
import com.blog.services.comment.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final ICommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addComment(@RequestBody CommentRequestDTO dto, Principal principal){
        CommentResponseDTO responseDTO = commentService.addComment(dto, principal);

        SuccessResponse response = SuccessResponse.builder()
                .message("Comment added successfully")
                .success(true)
                .data(responseDTO)
                .build();

        return ResponseEntity.ok(response);
    }


     @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable String commentId, Principal principal){
        commentService.deleteComment(commentId, principal);

        SuccessResponse response = SuccessResponse.builder()
                .message("Comment deleted successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

}
