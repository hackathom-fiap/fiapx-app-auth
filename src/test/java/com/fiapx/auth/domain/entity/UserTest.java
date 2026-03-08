package com.fiapx.auth.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Should create User with builder")
    void shouldCreateUserWithBuilder() {
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        User user = User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("Should create User with default constructor")
    void shouldCreateUserWithDefaultConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }

    @Test
    @DisplayName("Should create User with all args constructor")
    void shouldCreateUserWithAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        User user = new User(id, username, email, password);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("Should update User fields")
    void shouldUpdateUserFields() {
        User user = new User();
        UUID id = UUID.randomUUID();
        String username = "newuser";
        String email = "new@example.com";
        String password = "newpassword";

        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("Should verify equals and hashCode")
    void shouldVerifyEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        User user1 = User.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        User user2 = User.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        User user3 = User.builder()
                .id(UUID.randomUUID())
                .username("different")
                .email("different@example.com")
                .password("different")
                .build();

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    @DisplayName("Should verify toString")
    void shouldVerifyToString() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        String userString = user.toString();

        assertTrue(userString.contains("testuser"));
        assertTrue(userString.contains("test@example.com"));
    }
}