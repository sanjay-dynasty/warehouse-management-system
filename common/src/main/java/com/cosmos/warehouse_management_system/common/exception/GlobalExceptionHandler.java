package com.cosmos.warehouse_management_system.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cosmos.warehouse_management_system.common.response.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponse response = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(InvalidFileException.class)
        public ResponseEntity<ErrorResponse> handleInvalidFileException(
                        InvalidFileException ex,
                        HttpServletRequest request) {

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.badRequest().body(error);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(
                        BadRequestException ex,
                        HttpServletRequest request) {

                ErrorResponse response = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<ErrorResponse> handleConflict(
                        ConflictException ex,
                        HttpServletRequest request) {

                ErrorResponse response = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleException(
                        Exception ex,
                        HttpServletRequest request) {

                ErrorResponse response = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}