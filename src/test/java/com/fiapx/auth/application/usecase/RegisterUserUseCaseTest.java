package com.fiapx.auth.application.usecase;

import com.fiapx.auth.application.dto.UserRegistrationRequest;
import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.domain.repository.UserRepository;
import com.fiapx.auth.domain.service.PasswordEncoderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderPort passwordEncoder;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    private UserRegistrationRequest validRequest;
    private User savedUser;

    @BeforeEach
    void setUp() {
        validRequest = new UserRegistrationRequest();
        validRequest.setUsername("testuser");
        validRequest.setEmail("test@example.com");
        validRequest.setPassword("password123");

        savedUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    @DisplayName("Should register user successfully when data is valid")
    void shouldRegisterUserSuccessfullyWhenDataIsValid() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = registerUserUseCase.execute(validRequest);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertNotNull(result.getId());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> registerUserUseCase.execute(validRequest));

        assertEquals("Usuário já existe", exception.getMessage());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).existsByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> registerUserUseCase.execute(validRequest));

        assertEquals("E-mail já cadastrado", exception.getMessage());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when username and email already exist")
    void shouldThrowExceptionWhenUsernameAndEmailAlreadyExist() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> registerUserUseCase.execute(validRequest));

        assertEquals("Usuário já existe", exception.getMessage());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).existsByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should create user with encoded password")
    void shouldCreateUserWithEncodedPassword() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        registerUserUseCase.execute(validRequest);

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(argThat(user -> 
            "encodedPassword".equals(user.getPassword())
        ));
    }

    @Test
    @DisplayName("Should propagate exception when userRepository.save fails")
    void shouldPropagateExceptionWhenUserRepositorySaveFails() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> registerUserUseCase.execute(validRequest));

        assertEquals("Database error", exception.getMessage());

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should register user with different valid data")
    void shouldRegisterUserWithDifferentValidData() {
        UserRegistrationRequest differentRequest = new UserRegistrationRequest();
        differentRequest.setUsername("anotheruser");
        differentRequest.setEmail("another@example.com");
        differentRequest.setPassword("anotherpassword");

        User differentUser = User.builder()
                .id(UUID.randomUUID())
                .username("anotheruser")
                .email("another@example.com")
                .password("anotherEncodedPassword")
                .build();

        when(userRepository.existsByUsername("anotheruser")).thenReturn(false);
        when(userRepository.existsByEmail("another@example.com")).thenReturn(false);
        when(passwordEncoder.encode("anotherpassword")).thenReturn("anotherEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(differentUser);

        User result = registerUserUseCase.execute(differentRequest);

        assertNotNull(result);
        assertEquals("anotheruser", result.getUsername());
        assertEquals("another@example.com", result.getEmail());
        assertEquals("anotherEncodedPassword", result.getPassword());

        verify(userRepository).existsByUsername("anotheruser");
        verify(userRepository).existsByEmail("another@example.com");
        verify(passwordEncoder).encode("anotherpassword");
        verify(userRepository).save(any(User.class));
    }
}
