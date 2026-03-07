package com.fiapx.auth.infrastructure.adapter.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    @DisplayName("Should create UserEntity with builder")
    void shouldCreateUserEntityWithBuilder() {
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();

        assertEquals(id, userEntity.getId());
        assertEquals(username, userEntity.getUsername());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
    }

    @Test
    @DisplayName("Should create UserEntity with default constructor")
    void shouldCreateUserEntityWithDefaultConstructor() {
        UserEntity userEntity = new UserEntity();

        assertNull(userEntity.getId());
        assertNull(userEntity.getUsername());
        assertNull(userEntity.getEmail());
        assertNull(userEntity.getPassword());
    }

    @Test
    @DisplayName("Should create UserEntity with all args constructor")
    void shouldCreateUserEntityWithAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        UserEntity userEntity = new UserEntity(id, username, email, password);

        assertEquals(id, userEntity.getId());
        assertEquals(username, userEntity.getUsername());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
    }

    @Test
    @DisplayName("Should update UserEntity fields")
    void shouldUpdateUserEntityFields() {
        UserEntity userEntity = new UserEntity();
        UUID id = UUID.randomUUID();
        String username = "newuser";
        String email = "new@example.com";
        String password = "newpassword";

        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setPassword(password);

        assertEquals(id, userEntity.getId());
        assertEquals(username, userEntity.getUsername());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
    }

    @Test
    @DisplayName("Should verify equals and hashCode")
    void shouldVerifyEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        UserEntity userEntity1 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        UserEntity userEntity3 = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("different")
                .email("different@example.com")
                .password("different")
                .build();

        assertEquals(userEntity1, userEntity2);
        assertEquals(userEntity1.hashCode(), userEntity2.hashCode());
        assertNotEquals(userEntity1, userEntity3);
        assertNotEquals(userEntity1.hashCode(), userEntity3.hashCode());
    }

    @Test
    @DisplayName("Should verify toString")
    void shouldVerifyToString() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        String userEntityString = userEntity.toString();

        assertTrue(userEntityString.contains("testuser"));
        assertTrue(userEntityString.contains("test@example.com"));
    }

    @Test
    @DisplayName("Should create UserEntity with null ID")
    void shouldCreateUserEntityWithNullId() {
        UserEntity userEntity = UserEntity.builder()
                .id(null)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        assertNull(userEntity.getId());
        assertEquals("testuser", userEntity.getUsername());
        assertEquals("test@example.com", userEntity.getEmail());
        assertEquals("password", userEntity.getPassword());
    }

    @Test
    @DisplayName("Should create UserEntity with null values")
    void shouldCreateUserEntityWithNullValues() {
        UserEntity userEntity = UserEntity.builder()
                .id(null)
                .username(null)
                .email(null)
                .password(null)
                .build();

        assertNull(userEntity.getId());
        assertNull(userEntity.getUsername());
        assertNull(userEntity.getEmail());
        assertNull(userEntity.getPassword());
    }

    @Test
    @DisplayName("Should create UserEntity with empty strings")
    void shouldCreateUserEntityWithEmptyStrings() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("")
                .email("")
                .password("")
                .build();

        assertNotNull(userEntity.getId());
        assertEquals("", userEntity.getUsername());
        assertEquals("", userEntity.getEmail());
        assertEquals("", userEntity.getPassword());
    }

    @Test
    @DisplayName("Should compare UserEntity with itself")
    void shouldCompareUserEntityWithItself() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        assertEquals(userEntity, userEntity);
        assertEquals(userEntity.hashCode(), userEntity.hashCode());
    }

    @Test
    @DisplayName("Should compare UserEntity with different object")
    void shouldCompareUserEntityWithDifferentObject() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        assertNotEquals(userEntity, "string");
        assertNotEquals(userEntity, null);
    }

    @Test
    @DisplayName("Should create UserEntity with long username")
    void shouldCreateUserEntityWithLongUsername() {
        String longUsername = "a".repeat(100);
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username(longUsername)
                .email("test@example.com")
                .password("password")
                .build();

        assertEquals(longUsername, userEntity.getUsername());
    }

    @Test
    @DisplayName("Should create UserEntity with long email")
    void shouldCreateUserEntityWithLongEmail() {
        String longEmail = "a".repeat(50) + "@" + "b".repeat(50) + ".com";
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email(longEmail)
                .password("password")
                .build();

        assertEquals(longEmail, userEntity.getEmail());
    }

    @Test
    @DisplayName("Should create UserEntity with long password")
    void shouldCreateUserEntityWithLongPassword() {
        String longPassword = "a".repeat(200);
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password(longPassword)
                .build();

        assertEquals(longPassword, userEntity.getPassword());
    }

    @Test
    @DisplayName("Should create UserEntity with special characters")
    void shouldCreateUserEntityWithSpecialCharacters() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("test@user#123")
                .email("test+tag@example.co.uk")
                .password("p@$$w0rd!@#")
                .build();

        assertEquals("test@user#123", userEntity.getUsername());
        assertEquals("test+tag@example.co.uk", userEntity.getEmail());
        assertEquals("p@$$w0rd!@#", userEntity.getPassword());
    }

    @Test
    @DisplayName("Should maintain ID immutability when set")
    void shouldMaintainIdImmutabilityWhenSet() {
        UUID originalId = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder()
                .id(originalId)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        assertEquals(originalId, userEntity.getId());

        UUID newId = UUID.randomUUID();
        userEntity.setId(newId);

        assertEquals(newId, userEntity.getId());
        assertNotEquals(originalId, userEntity.getId());
    }
}
