package com.blog.utils.mappers;

import com.blog.dtos.comment.CommentRequestDTO;
import com.blog.dtos.comment.CommentResponseDTO;
import com.blog.entities.Comment;

public class CommentMapper {

    // toEntity
    public static Comment toEntity(CommentRequestDTO dto){
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        return comment;
    }


    // toDTO
    public static CommentResponseDTO toDTO(Comment comment){
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUser(UserMapper.toResponse(comment.getUser()));
        return dto;
    }
}
