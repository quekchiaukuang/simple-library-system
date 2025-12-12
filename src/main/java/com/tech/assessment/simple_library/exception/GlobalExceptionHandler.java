package com.tech.assessment.simple_library.exception;

import com.tech.assessment.simple_library.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookMetadataException.class)
    public ResponseEntity<ApiErrorResponse> handleBookMetadata(BookMetadataException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CONFLICT.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BOOKMETADATA_CONFLICT)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ApiErrorResponse> handleBookNotAvailable(BookNotAvailableException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CONFLICT.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BOOK_NOT_AVAILABLE)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BookNotBorrowedException.class)
    public ResponseEntity<ApiErrorResponse> handleBookNotBorrowed(BookNotBorrowedException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BOOK_NOT_BORROWED)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleBookNotFound(BookNotFoundException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BOOK_NOT_FOUND)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BorrowerEmailDuplicatedException.class)
    public ResponseEntity<ApiErrorResponse> handleBorrowerEmailDuplicated(BorrowerEmailDuplicatedException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CONFLICT.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BORROWER_DUPLICATED)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BorrowerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleBorrowerNotFound(BorrowerNotFoundException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BORROWER_NOT_FOUND)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BorrowerNotMatchedException.class)
    public ResponseEntity<ApiErrorResponse> handleBorrowerNotMatched(BorrowerNotMatchedException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CONFLICT.value())
                .errors(List.of(ErrorDto.builder()
                        .code(ErrorResponseConstant.CODE.BORROWER_NOT_MATCH)
                        .message(ex.getMessage())
                        .build()
                )).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(ValidationException ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(ex.getErrors())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
