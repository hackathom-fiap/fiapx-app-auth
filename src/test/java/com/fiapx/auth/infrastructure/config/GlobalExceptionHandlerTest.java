package com.fiapx.auth.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle RuntimeException with message")
    void shouldHandleRuntimeExceptionWithMessage() {
        RuntimeException exception = new RuntimeException("Test error message");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test error message", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Should handle RuntimeException without message")
    void shouldHandleRuntimeExceptionWithoutMessage() {
        RuntimeException exception = new RuntimeException((String) null);
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unknown error", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Should handle RuntimeException with empty message")
    void shouldHandleRuntimeExceptionWithEmptyMessage() {
        RuntimeException exception = new RuntimeException("");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Should handle custom RuntimeException")
    void shouldHandleCustomRuntimeException() {
        RuntimeException exception = new RuntimeException("Custom validation error");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Custom validation error", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Should handle RuntimeException with special characters")
    void shouldHandleRuntimeExceptionWithSpecialCharacters() {
        RuntimeException exception = new RuntimeException("Error: @#$%&*()");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error: @#$%&*()", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Should return response body with error key only")
    void shouldReturnResponseBodyWithErrorKeyOnly() {
        RuntimeException exception = new RuntimeException("Simple error");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().containsKey("error"));
    }
}
