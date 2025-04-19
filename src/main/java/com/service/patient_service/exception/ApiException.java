package com.service.patient_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final Integer statusCode;
    private final String message;
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
      super(message);
      this.message = message;
      this.httpStatus = httpStatus;
      this.statusCode = httpStatus.value();
    }
}
