package com.example.demo.exceptions;

public class InvalidException extends AppException {
	private static final long serialVersionUID = 1L;


    // Existing constructor
    public InvalidException(String message) {
        super(message);
    }

    // New constructor with cause
    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }

}
