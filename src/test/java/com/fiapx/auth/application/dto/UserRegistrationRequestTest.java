package com.fiapx.auth.application.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationRequestTest {

    @Test
    @DisplayName("Should create UserRegistrationRequest with valid data")
    void shouldCreateUserRegistrationRequestWithValidData() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        assertEquals("testuser", request.getUsername());
        assertEquals("test@example.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    @Test
    @DisplayName("Should create UserRegistrationRequest with default constructor")
    void shouldCreateUserRegistrationRequestWithDefaultConstructor() {
        UserRegistrationRequest request = new UserRegistrationRequest();

        assertNull(request.getUsername());
        assertNull(request.getEmail());
        assertNull(request.getPassword());
    }

    @Test
    @DisplayName("Should create UserRegistrationRequest with all args constructor")
    void shouldCreateUserRegistrationRequestWithAllArgsConstructor() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUsername("testuser");

        assertEquals("testuser", request.getUsername());
        assertEquals("test@example.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    @Test
    @DisplayName("Should update UserRegistrationRequest fields")
    void shouldUpdateUserRegistrationRequestFields() {
        UserRegistrationRequest request = new UserRegistrationRequest();

        request.setUsername("newuser");
        request.setEmail("new@example.com");
        request.setPassword("newpassword");

        assertEquals("newuser", request.getUsername());
        assertEquals("new@example.com", request.getEmail());
        assertEquals("newpassword", request.getPassword());
    }

    @Test
    @DisplayName("Should verify equals and hashCode")
    void shouldVerifyEqualsAndHashCode() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail("test@example.com");
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        UserRegistrationRequest request3 = new UserRegistrationRequest();
        request3.setUsername("different");
        request3.setEmail("test@example.com");
        request3.setPassword("password123");

        assertNotEquals(request1, request3);
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    @DisplayName("Should verify toString")
    void shouldVerifyToString() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        String requestString = request.toString();

        assertTrue(requestString.contains("testuser"));
        assertTrue(requestString.contains("test@example.com"));
        assertTrue(requestString.contains("password123"));
    }

    @Test
    @DisplayName("Should handle null values")
    void shouldHandleNullValues() {
        UserRegistrationRequest request = new UserRegistrationRequest();

        request.setUsername(null);
        request.setEmail(null);
        request.setPassword(null);

        assertNull(request.getUsername());
        assertNull(request.getEmail());
        assertNull(request.getPassword());
    }
}