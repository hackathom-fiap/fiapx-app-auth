package com.fiapx.auth.application.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    @DisplayName("Should create LoginResponse with builder")
    void shouldCreateLoginResponseWithBuilder() {
        LoginResponse response = LoginResponse.builder()
                .token("jwt-token")
                .username("testuser")
                .build();

        assertEquals("jwt-token", response.getToken());
        assertEquals("testuser", response.getUsername());
    }

    @Test
    @DisplayName("Should create LoginResponse with default constructor")
    void shouldCreateLoginResponseWithDefaultConstructor() {
        LoginResponse response = new LoginResponse();

        assertNull(response.getToken());
        assertNull(response.getUsername());
    }

    @Test
    @DisplayName("Should create LoginResponse with all args constructor")
    void shouldCreateLoginResponseWithAllArgsConstructor() {
        LoginResponse response = new LoginResponse("jwt-token", "testuser");

        assertEquals("jwt-token", response.getToken());
        assertEquals("testuser", response.getUsername());
    }

    @Test
    @DisplayName("Should update LoginResponse fields")
    void shouldUpdateLoginResponseFields() {
        LoginResponse response = new LoginResponse();

        response.setToken("new-token");
        response.setUsername("newuser");

        assertEquals("new-token", response.getToken());
        assertEquals("newuser", response.getUsername());
    }

    @Test
    @DisplayName("Should verify equals and hashCode")
    void shouldVerifyEqualsAndHashCode() {
        LoginResponse response1 = LoginResponse.builder()
                .token("jwt-token")
                .username("testuser")
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token")
                .username("testuser")
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());

        LoginResponse response3 = LoginResponse.builder()
                .token("different-token")
                .username("testuser")
                .build();

        assertNotEquals(response1, response3);
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    @DisplayName("Should verify toString")
    void shouldVerifyToString() {
        LoginResponse response = LoginResponse.builder()
                .token("jwt-token")
                .username("testuser")
                .build();

        String responseString = response.toString();

        assertTrue(responseString.contains("jwt-token"));
        assertTrue(responseString.contains("testuser"));
    }

    @Test
    @DisplayName("Should handle null values")
    void shouldHandleNullValues() {
        LoginResponse response = new LoginResponse();

        response.setToken(null);
        response.setUsername(null);

        assertNull(response.getToken());
        assertNull(response.getUsername());
    }
}