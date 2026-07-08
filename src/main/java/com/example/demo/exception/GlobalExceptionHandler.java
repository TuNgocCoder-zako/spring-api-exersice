package com.example.demo.exception;

import com.example.demo.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleUUIDError(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Invalid UUID format.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleRuntime(RuntimeException ex) {
        ex.printStackTrace();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCAGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCAGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AppExeption.class)
    public ResponseEntity<ApiResponse> handleRuntime(AppExeption ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String enumKey =ex.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.IVALID_MESSAGE;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e) {

        }


        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
