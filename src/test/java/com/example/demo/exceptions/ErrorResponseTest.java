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

        ErrorResponse errorResponse =
                new ErrorResponse(now, 400, "Validation Error", msgMap);

        assertThat(errorResponse)
                .extracting(
                        ErrorResponse::getTimestamp,
                        ErrorResponse::getStatus,
                        ErrorResponse::getError,
                        ErrorResponse::getMessage
                )
                .containsExactly(now, 400, "Validation Error", msgMap);
    }

    @Test
    void testNoArgsConstructorAndSetters() {

        LocalDateTime now = LocalDateTime.now();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(now);
        errorResponse.setStatus(500);
        errorResponse.setError("System Error");
        errorResponse.setMessage("Something went wrong");

        assertThat(errorResponse)
                .extracting(
                        ErrorResponse::getTimestamp,
                        ErrorResponse::getStatus,
                        ErrorResponse::getError,
                        ErrorResponse::getMessage
                )
                .containsExactly(now, 500, "System Error", "Something went wrong");
    }

    @Test
    void testMessageWithMapAndString() {

        ErrorResponse errorResponse = new ErrorResponse();

        Map<String, String> mapMsg = new HashMap<>();
        mapMsg.put("name", "must not be blank");

        errorResponse.setMessage(mapMsg);

        assertThat(errorResponse.getMessage())
                .isInstanceOf(Map.class);

        errorResponse.setMessage("Simple message");

        assertThat(errorResponse.getMessage())
                .isInstanceOf(String.class);
    }

    @Test
    void testEqualsAndHashCodeAndToString() {

        LocalDateTime now = LocalDateTime.now();

        ErrorResponse e1 = new ErrorResponse(now, 400, "Error", "Message");
        ErrorResponse e2 = new ErrorResponse(now, 400, "Error", "Message");
        ErrorResponse e3 = new ErrorResponse(now, 500, "Error", "Other");

        assertThat(e1)
                .isEqualTo(e2)
                .isNotEqualTo(e3)
                .hasSameHashCodeAs(e2)
                .doesNotHaveSameHashCodeAs(e3);

        assertThat(e1.toString())
                .contains("timestamp", "status", "error", "message");
    }
}

