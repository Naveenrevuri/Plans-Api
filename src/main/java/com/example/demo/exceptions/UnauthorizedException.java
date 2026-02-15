package com.example.demo.exceptions;

public class UnauthorizedException extends AppException{

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
