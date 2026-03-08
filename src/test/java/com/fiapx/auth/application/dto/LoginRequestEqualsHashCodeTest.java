package com.fiapx.auth.application.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestEqualsHashCodeTest {

    @Test
    @DisplayName("Should verify equals with null fields")
    void shouldVerifyEqualsWithNullFields() {
        LoginRequest request1 = new LoginRequest();
        LoginRequest request2 = new LoginRequest();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with equal objects")
    void shouldVerifyEqualsWithEqualObjects() {
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
    }

    @Test
    @DisplayName("Should verify equals with different username")
    void shouldVerifyEqualsWithDifferentUsername() {
        LoginRequest request1 = LoginRequest.builder()
                .username("user1")
                .password("password123")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("user2")
                .password("password123")
                .build();

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different password")
    void shouldVerifyEqualsWithDifferentPassword() {
        LoginRequest request1 = LoginRequest.builder()
                .username("testuser")
                .password("pass1")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("testuser")
                .password("pass2")
                .build();

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null username")
    void shouldVerifyEqualsWithNullUsername() {
        LoginRequest request1 = LoginRequest.builder()
                .username(null)
                .password("password123")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null password")
    void shouldVerifyEqualsWithNullPassword() {
        LoginRequest request1 = LoginRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null fields")
    void shouldVerifyEqualsWithBothNullFields() {
        LoginRequest request1 = LoginRequest.builder()
                .username(null)
                .password(null)
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username(null)
                .password(null)
                .build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null usernames")
    void shouldVerifyEqualsWithBothNullUsernames() {
        LoginRequest request1 = LoginRequest.builder()
                .username(null)
                .password("password123")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username(null)
                .password("password123")
                .build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null passwords")
    void shouldVerifyEqualsWithBothNullPasswords() {
        LoginRequest request1 = LoginRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null")
    void shouldVerifyEqualsWithNull() {
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        assertNotNull(request);
    }

    @Test
    @DisplayName("Should verify equals with different class")
    void shouldVerifyEqualsWithDifferentClass() {
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        assertFalse(request.equals("string"));
        assertFalse(request.equals(123));
    }

    @Test
    @DisplayName("Should verify hashCode consistency")
    void shouldVerifyHashCodeConsistency() {
        LoginRequest request = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        int hashCode1 = request.hashCode();
        int hashCode2 = request.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should verify hashCode with mixed null fields")
    void shouldVerifyHashCodeWithMixedNullFields() {
        LoginRequest request1 = LoginRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
