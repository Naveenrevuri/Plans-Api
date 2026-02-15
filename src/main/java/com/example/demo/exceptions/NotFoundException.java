package com.example.demo.exceptions;



public class NotFoundException extends AppException{
	private static final long serialVersionUID = 1L;


    // Existing constructor
    public NotFoundException(String message) {
        super(message);
    }

    // New constructor with cause
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
