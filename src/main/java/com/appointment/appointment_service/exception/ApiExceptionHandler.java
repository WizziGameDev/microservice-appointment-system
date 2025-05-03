package com.appointment.appointment_service.exception;

import com.appointment.appointment_service.dto.WebResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ApiExceptionHandler {

    // Exception Standards
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<WebResponse<Object>> handler(ApiException exception) {
        List<String> errorMessages = Collections.singletonList(exception.getMessage());

        WebResponse<Object> response = WebResponse.builder()
                .statusCode(exception.getStatusCode())
                .data(null)
                .errors(errorMessages)
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

    // Exception for Validation Parameter
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<Object>> handlerConstraintViolation(ConstraintViolationException exception) {
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            errorMessages.add(constraintViolation.getMessage());
        }

        WebResponse<Object> response = WebResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .errors(errorMessages)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Exception for Validation JSON
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<Object>> handlerMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errorDetails = new HashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorDetails.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        WebResponse<Object> response = WebResponse.builder()
                .statusCode(exception.getStatusCode().value())
                .data(null)
                .errors(errorDetails)
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

    // Exception for SQL Data Integrity
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<WebResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        String message = extractConstraintViolationMessage(exception);

        WebResponse<Object> response = WebResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .data(null)
                .errors(Collections.singletonList(message))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    private String extractConstraintViolationMessage(DataIntegrityViolationException exception) {
        Throwable cause = exception.getRootCause();
        if (cause instanceof java.sql.SQLIntegrityConstraintViolationException sqlException) {
            return sqlException.getMessage();
        }
        return "Data integrity violation";
    }
}
