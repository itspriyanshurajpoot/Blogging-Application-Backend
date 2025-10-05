package com.blog.utils.exceptions;

import com.blog.responses.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Validation failed")
                .success(false)
                .errorMessage("Invalid input")
                .validationErrors(ex.getFieldErrors().stream()
                        .collect(java.util.stream.Collectors.toMap(
                                fieldError -> fieldError.getField(),
                                fieldError -> fieldError.getDefaultMessage()
                        )))
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Resource not found")
                .success(false)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Resource already exists")
                .success(false)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(409).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("An error occurred")
                .success(false)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }
}
