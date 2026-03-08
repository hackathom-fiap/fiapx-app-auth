package com.fiapx.auth.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDataAnnotationTest {

    @Test
    @DisplayName("Should verify equals with null fields")
    void shouldVerifyEqualsWithNullFields() {
        User user1 = new User();
        User user2 = new User();

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with mixed null fields")
    void shouldVerifyEqualsWithMixedNullFields() {
        UUID id = UUID.randomUUID();
        User user1 = new User(id, "user", "user@example.com", "pass");
        User user2 = new User(id, null, null, null);
        User user3 = new User(null, "user", "user@example.com", "pass");

        assertNotEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user2, user3);
    }

    @Test
    @DisplayName("Should verify equals with different class")
    void shouldVerifyEqualsWithDifferentClass() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        assertFalse(user.equals("string"));
        assertFalse(user.equals(null));
    }

    @Test
    @DisplayName("Should verify equals with different id only")
    void shouldVerifyEqualsWithDifferentIdOnly() {
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        User user2 = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("Should verify equals with different username only")
    void shouldVerifyEqualsWithDifferentUsernameOnly() {
        UUID id = UUID.randomUUID();
        User user1 = User.builder()
                .id(id)
                .username("user1")
                .email("test@example.com")
                .password("password")
                .build();

        User user2 = User.builder()
                .id(id)
                .username("user2")
                .email("test@example.com")
                .password("password")
                .build();

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("Should verify equals with different email only")
    void shouldVerifyEqualsWithDifferentEmailOnly() {
        UUID id = UUID.randomUUID();
        User user1 = User.builder()
                .id(id)
                .username("testuser")
                .email("email1@example.com")
                .password("password")
                .build();

        User user2 = User.builder()
                .id(id)
                .username("testuser")
                .email("email2@example.com")
                .password("password")
                .build();

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("Should verify equals with different password only")
    void shouldVerifyEqualsWithDifferentPasswordOnly() {
        UUID id = UUID.randomUUID();
        User user1 = User.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password1")
                .build();

        User user2 = User.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password2")
                .build();

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("Should verify toString contains all fields")
    void shouldVerifyToStringContainsAllFields() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        String userString = user.toString();

        assertTrue(userString.contains("testuser"));
        assertTrue(userString.contains("test@example.com"));
        assertTrue(userString.contains("password123"));
        assertTrue(userString.contains(id.toString()));
    }

    @Test
    @DisplayName("Should verify toString with null fields")
    void shouldVerifyToStringWithNullFields() {
        User user = new User();
        String userString = user.toString();

        assertNotNull(userString);
    }
}
