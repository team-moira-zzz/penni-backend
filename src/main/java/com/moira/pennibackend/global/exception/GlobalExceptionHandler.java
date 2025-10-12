package com.moira.pennibackend.global.exception;

import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import com.moira.pennibackend.global.exception.custom.PenniUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = PenniUserException.class)
    public ResponseEntity<ErrorResponse> handlePenniUserException(PenniUserException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(e.getErrorCode())
                .status(e.getHttpStatus())
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = PenniGroupException.class)
    public ResponseEntity<ErrorResponse> handlePenniGroupException(PenniGroupException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(e.getErrorCode())
                .status(e.getHttpStatus())
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}
