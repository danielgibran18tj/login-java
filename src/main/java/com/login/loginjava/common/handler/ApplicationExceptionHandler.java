package com.login.loginjava.common.handler;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.common.exception.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<ErrorResponse> handleApiException(ApplicationException applicationException) {
        var errorResponse = new ErrorResponse();
        errorResponse.setCode(applicationException.getCode());
        errorResponse.setMessage(applicationException.getMessage());
        log.error("ApplicationExceptionHandler", applicationException);
        return new ResponseEntity<ErrorResponse>(errorResponse, applicationException.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleApiException(ConstraintViolationException applicationException) {
        var errorResponse = new ErrorResponse();
        errorResponse.setCode(applicationException.getConstraintName());
        errorResponse.setMessage(applicationException.getLocalizedMessage());
        log.error("ApplicationExceptionHandler", applicationException);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setCode("BAD_REQUEST");
        errorResponse.setMessage(ex.getBindingResult().getFieldErrors().stream().map( it -> it.getField() + "->" + it.getDefaultMessage()).collect(Collectors.joining(",")));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
