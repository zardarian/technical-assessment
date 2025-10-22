package com.example.listing.exception;

import com.example.listing.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OwnerException.class)
    public ResponseEntity<BaseResponse<String>> handleOwnerNotFound(OwnerException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<String>> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.fail(e.getMessage()));
    }
}
