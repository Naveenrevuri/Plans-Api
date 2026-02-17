package com.example.demo.exceptions;



import java.util.HashMap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	


    // AlreadyExistsException → 409
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExists(AlreadyExistsException ex) {
    	 logger.warn("AlreadyExistsException occurred: {}", ex.getMessage());
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),  // 409
                "Already Exists",
                ex.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    
    
    // Validation Errors → 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(
	        MethodArgumentNotValidException ex) {
       
	    logger.warn("Validation failed");
	    Map<String, String> message = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error -> 
	    	message.put(error.getField(), error.getDefaultMessage())
	);

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),                 //  timestamp
	            HttpStatus.BAD_REQUEST.value(),     // error status
	            "Validation Error",                 // type of error
	            message                             // message
	    );

	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}



	   // NotFoundException → 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
     

        logger.warn("NotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),  // 404
                "Resource Not Found",
                ex.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    
   
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatus(
            InvalidException ex) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid Status",
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(response);
    }

    
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(Exception ex) {

        return ResponseEntity.status(403).body(
            Map.of(
                "status", 403,
                "error", "Forbidden",
                "message", "You don't have permission"
            )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {

        return ResponseEntity.status(500).body(
            Map.of(
                "status", 500,
                "error", "System Error",
                "message", "Something went wrong. Please try again later."
            )
        );
    }

}
