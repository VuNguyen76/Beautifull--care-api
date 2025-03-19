package com.dailycodework.beautifulcare.exception;

import com.dailycodework.beautifulcare.dto.response.APIResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIResponse<Object>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        APIResponse<Object> response = new APIResponse<>(
                false,
                errorCode.getMessage(),
                null);
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        APIResponse<Map<String, String>> response = new APIResponse<>(
                false,
                "Validation error",
                errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Object>> handleConstraintViolationException(
            ConstraintViolationException exception) {
        APIResponse<Object> response = new APIResponse<>(
                false,
                exception.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleGenericException(Exception exception) {
        APIResponse<Object> response = new APIResponse<>(
                false,
                "An unexpected error occurred: " + exception.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
