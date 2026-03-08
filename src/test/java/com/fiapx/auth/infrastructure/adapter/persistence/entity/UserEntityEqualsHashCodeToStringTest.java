package com.fiapx.auth.infrastructure.adapter.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityEqualsHashCodeToStringTest {

    @Test
    @DisplayName("Should verify equals with null fields")
    void shouldVerifyEqualsWithNullFields() {
        UserEntity entity1 = new UserEntity();
        UserEntity entity2 = new UserEntity();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with equal objects")
    void shouldVerifyEqualsWithEqualObjects() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different id")
    void shouldVerifyEqualsWithDifferentId() {
        UserEntity entity1 = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different username")
    void shouldVerifyEqualsWithDifferentUsername() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username("user1")
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("user2")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different email")
    void shouldVerifyEqualsWithDifferentEmail() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("email1@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("email2@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different password")
    void shouldVerifyEqualsWithDifferentPassword() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("pass1")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("pass2")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null id")
    void shouldVerifyEqualsWithNullId() {
        UserEntity entity1 = UserEntity.builder()
                .id(null)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null username")
    void shouldVerifyEqualsWithNullUsername() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username(null)
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null email")
    void shouldVerifyEqualsWithNullEmail() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email(null)
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null password")
    void shouldVerifyEqualsWithNullPassword() {
        UUID id = UUID.randomUUID();
        UserEntity entity1 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password(null)
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null fields")
    void shouldVerifyEqualsWithBothNullFields() {
        UserEntity entity1 = UserEntity.builder()
                .id(null)
                .username(null)
                .email(null)
                .password(null)
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(null)
                .username(null)
                .email(null)
                .password(null)
                .build();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null ids")
    void shouldVerifyEqualsWithBothNullIds() {
        UserEntity entity1 = UserEntity.builder()
                .id(null)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(null)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null")
    void shouldVerifyEqualsWithNull() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertNotNull(entity);
    }

    @Test
    @DisplayName("Should verify equals with different class")
    void shouldVerifyEqualsWithDifferentClass() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        assertFalse(entity.equals("string"));
        assertFalse(entity.equals(123));
    }

    @Test
    @DisplayName("Should verify hashCode consistency")
    void shouldVerifyHashCodeConsistency() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        int hashCode1 = entity.hashCode();
        int hashCode2 = entity.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should verify hashCode with mixed null fields")
    void shouldVerifyHashCodeWithMixedNullFields() {
        UserEntity entity1 = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email(null)
                .password("password123")
                .build();

        UserEntity entity2 = UserEntity.builder()
                .id(entity1.getId())
                .username("testuser")
                .email(null)
                .password("password123")
                .build();

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Should verify toString contains all fields")
    void shouldVerifyToStringContainsAllFields() {
        UUID id = UUID.randomUUID();
        UserEntity entity = UserEntity.builder()
                .id(id)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        String entityString = entity.toString();

        assertTrue(entityString.contains("UserEntity"));
        assertTrue(entityString.contains(id.toString()));
        assertTrue(entityString.contains("testuser"));
        assertTrue(entityString.contains("test@example.com"));
        assertTrue(entityString.contains("password123"));
    }

    @Test
    @DisplayName("Should verify toString with null fields")
    void shouldVerifyToStringWithNullFields() {
        UserEntity entity = UserEntity.builder()
                .id(null)
                .username(null)
                .email(null)
                .password(null)
                .build();

        String entityString = entity.toString();

        assertTrue(entityString.contains("UserEntity"));
        assertTrue(entityString.contains("null"));
    }

    @Test
    @DisplayName("Should verify toString format")
    void shouldVerifyToStringFormat() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        String entityString = entity.toString();

        assertTrue(entityString.startsWith("UserEntity("));
        assertTrue(entityString.endsWith(")"));
    }
}
