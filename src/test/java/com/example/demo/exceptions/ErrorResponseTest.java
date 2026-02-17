package com.example.demo.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("field", "must not be null");

        ErrorResponse errorResponse = new ErrorResponse(now, 400, "Validation Error", msgMap);

        assertThat(errorResponse.getTimestamp()).isEqualTo(now);
        assertThat(errorResponse.getStatus()).isEqualTo(400);
        assertThat(errorResponse.getError()).isEqualTo("Validation Error");
        assertThat(errorResponse.getMessage()).isEqualTo(msgMap);
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ErrorResponse errorResponse = new ErrorResponse();

        LocalDateTime now = LocalDateTime.now();
        errorResponse.setTimestamp(now);
        errorResponse.setStatus(500);
        errorResponse.setError("System Error");
        errorResponse.setMessage("Something went wrong");

        assertThat(errorResponse.getTimestamp()).isEqualTo(now);
        assertThat(errorResponse.getStatus()).isEqualTo(500);
        assertThat(errorResponse.getError()).isEqualTo("System Error");
        assertThat(errorResponse.getMessage()).isEqualTo("Something went wrong");
    }

    @Test
    void testMessageWithMapAndString() {
        ErrorResponse errorResponse = new ErrorResponse();

        Map<String, String> mapMsg = new HashMap<>();
        mapMsg.put("name", "must not be blank");
        errorResponse.setMessage(mapMsg);
        assertThat(errorResponse.getMessage()).isInstanceOf(Map.class);

        errorResponse.setMessage("Simple message");
        assertThat(errorResponse.getMessage()).isInstanceOf(String.class);
    }

    @Test
    void testEqualsAndHashCodeAndToString() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse e1 = new ErrorResponse(now, 400, "Error", "Message");
        ErrorResponse e2 = new ErrorResponse(now, 400, "Error", "Message");
        ErrorResponse e3 = new ErrorResponse(now, 500, "Error", "Other");

        // equals
        assertThat(e1).isEqualTo(e2);
        assertThat(e1).isNotEqualTo(e3);

        // hashCode
        assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
        assertThat(e1.hashCode()).isNotEqualTo(e3.hashCode());

        // toString
        String str = e1.toString();
        assertThat(str).contains("timestamp", "status", "error", "message");
    }
}

