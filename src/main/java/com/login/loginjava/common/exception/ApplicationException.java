package com.login.loginjava.common.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends Exception{
    private HttpStatus httpStatus;
    private String code;
    private String message;

    public ApplicationException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApplicationException(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }



    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
