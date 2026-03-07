package com.fiapx.auth.infrastructure.adapter.persistence;

import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.infrastructure.adapter.persistence.entity.UserEntity;
import com.fiapx.auth.infrastructure.adapter.persistence.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private JpaUserRepository jpaRepository;

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    private User testUser;
    private UserEntity testUserEntity;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        
        testUser = User.builder()
                .id(testId)
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        testUserEntity = UserEntity.builder()
                .id(testId)
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUserSuccessfully() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);

        User result = userPersistenceAdapter.save(testUser);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());

        verify(jpaRepository).save(argThat(entity ->
                entity.getId().equals(testId) &&
                entity.getUsername().equals("testuser") &&
                entity.getEmail().equals("test@example.com") &&
                entity.getPassword().equals("encodedPassword")
        ));
    }

    @Test
    @DisplayName("Should save user without ID")
    void shouldSaveUserWithoutId() {
        User userWithoutId = User.builder()
                .username("newuser")
                .email("new@example.com")
                .password("newPassword")
                .build();

        UserEntity savedEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("newuser")
                .email("new@example.com")
                .password("newPassword")
                .build();

        when(jpaRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        User result = userPersistenceAdapter.save(userWithoutId);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("newuser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("newPassword", result.getPassword());

        verify(jpaRepository).save(argThat(entity ->
                entity.getUsername().equals("newuser") &&
                entity.getEmail().equals("new@example.com") &&
                entity.getPassword().equals("newPassword")
        ));
    }

    @Test
    @DisplayName("Should find user by username successfully")
    void shouldFindUserByUsernameSuccessfully() {
        when(jpaRepository.findByUsername("testuser")).thenReturn(Optional.of(testUserEntity));

        Optional<User> result = userPersistenceAdapter.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("testuser", result.get().getUsername());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals("encodedPassword", result.get().getPassword());

        verify(jpaRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should return empty Optional when user not found by username")
    void shouldReturnEmptyOptionalWhenUserNotFoundByUsername() {
        when(jpaRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<User> result = userPersistenceAdapter.findByUsername("nonexistent");

        assertFalse(result.isPresent());

        verify(jpaRepository).findByUsername("nonexistent");
    }

    @Test
    @DisplayName("Should find user by email successfully")
    void shouldFindUserByEmailSuccessfully() {
        when(jpaRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUserEntity));

        Optional<User> result = userPersistenceAdapter.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("testuser", result.get().getUsername());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals("encodedPassword", result.get().getPassword());

        verify(jpaRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should return empty Optional when user not found by email")
    void shouldReturnEmptyOptionalWhenUserNotFoundByEmail() {
        when(jpaRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<User> result = userPersistenceAdapter.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());

        verify(jpaRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("Should check if user exists by username")
    void shouldCheckIfUserExistsByUsername() {
        when(jpaRepository.existsByUsername("testuser")).thenReturn(true);

        boolean result = userPersistenceAdapter.existsByUsername("testuser");

        assertTrue(result);

        verify(jpaRepository).existsByUsername("testuser");
    }

    @Test
    @DisplayName("Should check if user does not exist by username")
    void shouldCheckIfUserDoesNotExistsByUsername() {
        when(jpaRepository.existsByUsername("nonexistent")).thenReturn(false);

        boolean result = userPersistenceAdapter.existsByUsername("nonexistent");

        assertFalse(result);

        verify(jpaRepository).existsByUsername("nonexistent");
    }

    @Test
    @DisplayName("Should check if user exists by email")
    void shouldCheckIfUserExistsByEmail() {
        when(jpaRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = userPersistenceAdapter.existsByEmail("test@example.com");

        assertTrue(result);

        verify(jpaRepository).existsByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should check if user does not exist by email")
    void shouldCheckIfUserDoesNotExistsByEmail() {
        when(jpaRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

        boolean result = userPersistenceAdapter.existsByEmail("nonexistent@example.com");

        assertFalse(result);

        verify(jpaRepository).existsByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("Should propagate exception when saving user")
    void shouldPropagateExceptionWhenSavingUser() {
        when(jpaRepository.save(any(UserEntity.class)))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userPersistenceAdapter.save(testUser));

        verify(jpaRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should propagate exception when finding by username")
    void shouldPropagateExceptionWhenFindingByUsername() {
        when(jpaRepository.findByUsername("testuser"))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userPersistenceAdapter.findByUsername("testuser"));

        verify(jpaRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should propagate exception when finding by email")
    void shouldPropagateExceptionWhenFindingByEmail() {
        when(jpaRepository.findByEmail("test@example.com"))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userPersistenceAdapter.findByEmail("test@example.com"));

        verify(jpaRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should propagate exception when checking existence by username")
    void shouldPropagateExceptionWhenCheckingExistenceByUsername() {
        when(jpaRepository.existsByUsername("testuser"))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userPersistenceAdapter.existsByUsername("testuser"));

        verify(jpaRepository).existsByUsername("testuser");
    }

    @Test
    @DisplayName("Should propagate exception when checking existence by email")
    void shouldPropagateExceptionWhenCheckingExistenceByEmail() {
        when(jpaRepository.existsByEmail("test@example.com"))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userPersistenceAdapter.existsByEmail("test@example.com"));

        verify(jpaRepository).existsByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should correctly map UserEntity to User")
    void shouldCorrectlyMapUserEntityToUser() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("mappeduser")
                .email("mapped@example.com")
                .password("mappedPassword")
                .build();

        when(jpaRepository.save(any(UserEntity.class))).thenReturn(entity);

        User savedUser = userPersistenceAdapter.save(User.builder()
                .username("mappeduser")
                .email("mapped@example.com")
                .password("mappedPassword")
                .build());

        assertEquals(entity.getId(), savedUser.getId());
        assertEquals(entity.getUsername(), savedUser.getUsername());
        assertEquals(entity.getEmail(), savedUser.getEmail());
        assertEquals(entity.getPassword(), savedUser.getPassword());
    }

    @Test
    @DisplayName("Should handle null values when saving")
    void shouldHandleNullValuesWhenSaving() {
        User userWithNulls = User.builder()
                .id(null)
                .username("nulluser")
                .email(null)
                .password("password")
                .build();

        UserEntity entityWithNulls = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("nulluser")
                .email(null)
                .password("password")
                .build();

        when(jpaRepository.save(any(UserEntity.class))).thenReturn(entityWithNulls);

        User result = userPersistenceAdapter.save(userWithNulls);

        assertNotNull(result);
        assertEquals("nulluser", result.getUsername());
        assertNull(result.getEmail());
        assertEquals("password", result.getPassword());

        verify(jpaRepository).save(argThat(entity ->
                entity.getUsername().equals("nulluser") &&
                entity.getEmail() == null &&
                entity.getPassword().equals("password")
        ));
    }
}
