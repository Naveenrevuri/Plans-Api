package com.example.demo.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExceptionClassesTest {

    // ---------------- NotFoundException Tests ----------------
    @Test
    void testNotFoundException_MessageConstructor() {
        String msg = "Plan not found";
        NotFoundException ex = new NotFoundException(msg);
        assertEquals(msg, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testNotFoundException_MessageCauseConstructor() {
        String msg = "Plan not found";
        Throwable cause = new RuntimeException("Cause");
        NotFoundException ex = new NotFoundException(msg, cause);
        assertEquals(msg, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // ---------------- InvalidException Tests ----------------
    @Test
    void testInvalidException_MessageConstructor() {
        String msg = "Invalid status";
        InvalidException ex = new InvalidException(msg);
        assertEquals(msg, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testInvalidException_MessageCauseConstructor() {
        String msg = "Invalid status";
        Throwable cause = new RuntimeException("Cause");
        InvalidException ex = new InvalidException(msg, cause);
        assertEquals(msg, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // ---------------- AlreadyExistsException Tests ----------------
    @Test
    void testAlreadyExistsException_MessageConstructor() {
        String msg = "Plan already exists";
        AlreadyExistsException ex = new AlreadyExistsException(msg);
        assertEquals(msg, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testAlreadyExistsException_MessageCauseConstructor() {
        String msg = "Plan already exists";
        Throwable cause = new RuntimeException("Cause");
        AlreadyExistsException ex = new AlreadyExistsException(msg, cause);
        assertEquals(msg, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // ---------------- UnauthorizedException Tests ----------------
    @Test
    void testUnauthorizedException_MessageCauseConstructor() {
        String msg = "Unauthorized access";
        Throwable cause = new RuntimeException("Cause");
        UnauthorizedException ex = new UnauthorizedException(msg, cause);
        assertEquals(msg, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

