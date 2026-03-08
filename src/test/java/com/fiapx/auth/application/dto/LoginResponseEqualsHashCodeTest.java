package com.fiapx.auth.application.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseEqualsHashCodeTest {

    @Test
    @DisplayName("Should verify equals with null fields")
    void shouldVerifyEqualsWithNullFields() {
        LoginResponse response1 = new LoginResponse();
        LoginResponse response2 = new LoginResponse();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with equal objects")
    void shouldVerifyEqualsWithEqualObjects() {
        LoginResponse response1 = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different token")
    void shouldVerifyEqualsWithDifferentToken() {
        LoginResponse response1 = LoginResponse.builder()
                .token("token-1")
                .username("testuser")
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("token-2")
                .username("testuser")
                .build();

        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different username")
    void shouldVerifyEqualsWithDifferentUsername() {
        LoginResponse response1 = LoginResponse.builder()
                .token("jwt-token-123")
                .username("user1")
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token-123")
                .username("user2")
                .build();

        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null token")
    void shouldVerifyEqualsWithNullToken() {
        LoginResponse response1 = LoginResponse.builder()
                .token(null)
                .username("testuser")
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null username")
    void shouldVerifyEqualsWithNullUsername() {
        LoginResponse response1 = LoginResponse.builder()
                .token("jwt-token-123")
                .username(null)
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null fields")
    void shouldVerifyEqualsWithBothNullFields() {
        LoginResponse response1 = LoginResponse.builder()
                .token(null)
                .username(null)
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token(null)
                .username(null)
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null tokens")
    void shouldVerifyEqualsWithBothNullTokens() {
        LoginResponse response1 = LoginResponse.builder()
                .token(null)
                .username("testuser")
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token(null)
                .username("testuser")
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null usernames")
    void shouldVerifyEqualsWithBothNullUsernames() {
        LoginResponse response1 = LoginResponse.builder()
                .token("jwt-token-123")
                .username(null)
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token-123")
                .username(null)
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null")
    void shouldVerifyEqualsWithNull() {
        LoginResponse response = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should verify equals with different class")
    void shouldVerifyEqualsWithDifferentClass() {
        LoginResponse response = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        assertFalse(response.equals("string"));
        assertFalse(response.equals(123));
    }

    @Test
    @DisplayName("Should verify hashCode consistency")
    void shouldVerifyHashCodeConsistency() {
        LoginResponse response = LoginResponse.builder()
                .token("jwt-token-123")
                .username("testuser")
                .build();

        int hashCode1 = response.hashCode();
        int hashCode2 = response.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should verify hashCode with mixed null fields")
    void shouldVerifyHashCodeWithMixedNullFields() {
        LoginResponse response1 = LoginResponse.builder()
                .token("jwt-token-123")
                .username(null)
                .build();

        LoginResponse response2 = LoginResponse.builder()
                .token("jwt-token-123")
                .username(null)
                .build();

        assertEquals(response1.hashCode(), response2.hashCode());
    }
}
