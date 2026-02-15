package com.example.demo.exceptions;


public class AlreadyExistsException extends AppException{

	private static final long serialVersionUID = 1L;

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
