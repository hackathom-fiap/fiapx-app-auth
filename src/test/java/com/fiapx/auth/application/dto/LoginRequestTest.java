package com.fiapx.auth.application.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    @DisplayName("Should create LoginRequest with builder")
    void shouldCreateLoginRequestWithBuilder() {
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        assertEquals("testuser", request.getUsername());
        assertEquals("password123", request.getPassword());
    }

    @Test
    @DisplayName("Should create LoginRequest with default constructor")
    void shouldCreateLoginRequestWithDefaultConstructor() {
        LoginRequest request = new LoginRequest();

        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }

    @Test
    @DisplayName("Should create LoginRequest with all args constructor")
    void shouldCreateLoginRequestWithAllArgsConstructor() {
        LoginRequest request = new LoginRequest("testuser", "password123");

        assertEquals("testuser", request.getUsername());
        assertEquals("password123", request.getPassword());
    }

    @Test
    @DisplayName("Should update LoginRequest fields")
    void shouldUpdateLoginRequestFields() {
        LoginRequest request = new LoginRequest();

        request.setUsername("newuser");
        request.setPassword("newpassword");

        assertEquals("newuser", request.getUsername());
        assertEquals("newpassword", request.getPassword());
    }

    @Test
    @DisplayName("Should verify equals and hashCode")
    void shouldVerifyEqualsAndHashCode() {
        LoginRequest request1 = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        LoginRequest request3 = LoginRequest.builder()
                .username("different")
                .password("password123")
                .build();

        assertNotEquals(request1, request3);
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    @DisplayName("Should verify toString")
    void shouldVerifyToString() {
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        String requestString = request.toString();

        assertTrue(requestString.contains("testuser"));
        assertTrue(requestString.contains("password123"));
    }

    @Test
    @DisplayName("Should handle null values")
    void shouldHandleNullValues() {
        LoginRequest request = new LoginRequest();

        request.setUsername(null);
        request.setPassword(null);

        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }
}