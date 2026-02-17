package com.example.demo.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    // ---------------- AlreadyExistsException ----------------
    @Test
    void testHandleAlreadyExistsException() {
        AlreadyExistsException ex = new AlreadyExistsException("Plan already exists");
        ResponseEntity<ErrorResponse> response = handler.handleAlreadyExists(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(409);
        ErrorResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo(409);
        assertThat(body.getError()).isEqualTo("Already Exists");
        assertThat(body.getMessage()).isEqualTo("Plan already exists");
    }

    // ---------------- NotFoundException ----------------
    @Test
    void testHandleNotFoundException() {
        NotFoundException ex = new NotFoundException("Plan not found");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        ErrorResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo(404);
        assertThat(body.getError()).isEqualTo("Resource Not Found");
        assertThat(body.getMessage()).isEqualTo("Plan not found");
    }

    // ---------------- InvalidException ----------------
    @Test
    void testHandleInvalidException() {
        InvalidException ex = new InvalidException("Invalid status");
        ResponseEntity<ErrorResponse> response = handler.handleInvalidStatus(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(400); // ResponseEntity.badRequest()
        ErrorResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo(401); // as per handler
        assertThat(body.getError()).isEqualTo("Invalid Status");
        assertThat(body.getMessage()).isEqualTo("Invalid status");
    }

    // ---------------- AccessDeniedException ----------------
    @Test
    void testHandleAccessDeniedException() {
        org.springframework.security.access.AccessDeniedException ex =
                new org.springframework.security.access.AccessDeniedException("No permission");

        ResponseEntity<?> response = handler.handleAccessDeniedException(ex);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);

        // Cast to Map<String, Object> for safe containsEntry
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertThat(body).containsEntry("status", 403);
        assertThat(body).containsEntry("error", "Forbidden");
        assertThat(body).containsEntry("message", "You don't have permission");
    }

    // ---------------- Generic Exception ----------------
    @Test
    void testHandleAllException() {
        Exception ex = new Exception("Some error");
        ResponseEntity<?> response = handler.handleAll(ex);
        assertThat(response.getStatusCodeValue()).isEqualTo(500);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertThat(body).containsEntry("status", 500);
        assertThat(body).containsEntry("error", "System Error");
        assertThat(body).containsEntry("message", "Something went wrong. Please try again later.");
    }

    // ---------------- MethodArgumentNotValidException ----------------
    @Test
    void testHandleValidationException() {
        BindingResult bindingResult = org.mockito.Mockito.mock(BindingResult.class);
        FieldError fieldError = new FieldError("plan", "name", "must not be blank");
        org.mockito.Mockito.when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = handler.handleValidation(ex);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        ErrorResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo(400);
        assertThat(body.getError()).isEqualTo("Validation Error");

        Map<String, String> messageMap = (Map<String, String>) body.getMessage();
        assertThat(messageMap).containsEntry("name", "must not be blank");
    }
}
