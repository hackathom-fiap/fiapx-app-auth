package com.fiapx.auth.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserBuilderTest {

    @Test
    @DisplayName("Should verify UserBuilder toString")
    void shouldVerifyUserBuilderToString() {
        UUID id = UUID.randomUUID();
        User.UserBuilder builder = User.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123");

        String builderString = builder.toString();

        assertTrue(builderString.contains("testuser"));
        assertTrue(builderString.contains("test@example.com"));
        assertTrue(builderString.contains("password123"));
        assertTrue(builderString.contains(id.toString()));
    }

    @Test
    @DisplayName("Should create User with empty builder")
    void shouldCreateUserWithEmptyBuilder() {
        User.UserBuilder builder = User.builder();
        String builderString = builder.toString();

        assertNotNull(builderString);
    }

    @Test
    @DisplayName("Should verify UserBuilder with partial data")
    void shouldVerifyUserBuilderWithPartialData() {
        User.UserBuilder builder = User.builder()
                .username("partialuser");

        String builderString = builder.toString();

        assertTrue(builderString.contains("partialuser"));
    }
}
