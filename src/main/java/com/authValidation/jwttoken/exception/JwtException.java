package com.authValidation.jwttoken.exception;

import org.springframework.http.HttpStatus;

public class JwtException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public JwtException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
