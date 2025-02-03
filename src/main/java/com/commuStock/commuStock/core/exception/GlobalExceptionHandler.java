package com.commuStock.commuStock.core.exception;

import com.commuStock.commuStock.core.enums.ErrorCode;
import com.commuStock.commuStock.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ResourceNotFoundException을 ErrorCode를 이용하여 처리
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
//        ApiResponse<String> response = new ApiResponse<>(
//                ErrorCode.ERROR,
//                ex.getMessage(),
//                null
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

    // 다른 예외 처리 예시
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        ApiResponse<String> response = new ApiResponse<>(
                ErrorCode.ERROR.getCode(),
                ErrorCode.ERROR.getMessage(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
