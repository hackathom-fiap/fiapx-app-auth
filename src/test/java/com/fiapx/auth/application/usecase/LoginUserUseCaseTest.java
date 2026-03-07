package com.fiapx.auth.application.usecase;

import com.fiapx.auth.application.dto.LoginRequest;
import com.fiapx.auth.application.dto.LoginResponse;
import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.domain.repository.UserRepository;
import com.fiapx.auth.domain.service.PasswordEncoderPort;
import com.fiapx.auth.domain.service.TokenServicePort;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderPort passwordEncoder;

    @Mock
    private TokenServicePort tokenService;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    private LoginRequest validLoginRequest;
    private User existingUser;

    @BeforeEach
    void setUp() {
        validLoginRequest = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        existingUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    @DisplayName("Should login successfully when credentials are valid")
    void shouldLoginSuccessfullyWhenCredentialsAreValid() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(existingUser)).thenReturn("jwt-token");

        LoginResponse result = loginUserUseCase.execute(validLoginRequest);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
        assertEquals("testuser", result.getUsername());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password123", "encodedPassword");
        verify(tokenService).generateToken(existingUser);
    }

    @Test
    @DisplayName("Should throw exception when user does not exist")
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginUserUseCase.execute(validLoginRequest));

        assertEquals("Usuário ou senha inválidos", exception.getMessage());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when password is incorrect")
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginUserUseCase.execute(validLoginRequest));

        assertEquals("Usuário ou senha inválidos", exception.getMessage());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password123", "encodedPassword");
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when password is null")
    void shouldThrowExceptionWhenPasswordIsNull() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(isNull(), eq("encodedPassword"))).thenReturn(false);

        LoginRequest requestWithNullPassword = LoginRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginUserUseCase.execute(requestWithNullPassword));

        assertEquals("Usuário ou senha inválidos", exception.getMessage());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches(isNull(), eq("encodedPassword"));
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should login with different valid credentials")
    void shouldLoginWithDifferentValidCredentials() {
        LoginRequest differentRequest = LoginRequest.builder()
                .username("anotheruser")
                .password("anotherpassword")
                .build();

        User differentUser = User.builder()
                .id(UUID.randomUUID())
                .username("anotheruser")
                .email("another@example.com")
                .password("anotherEncodedPassword")
                .build();

        when(userRepository.findByUsername("anotheruser")).thenReturn(Optional.of(differentUser));
        when(passwordEncoder.matches("anotherpassword", "anotherEncodedPassword")).thenReturn(true);
        when(tokenService.generateToken(differentUser)).thenReturn("another-jwt-token");

        LoginResponse result = loginUserUseCase.execute(differentRequest);

        assertNotNull(result);
        assertEquals("another-jwt-token", result.getToken());
        assertEquals("anotheruser", result.getUsername());

        verify(userRepository).findByUsername("anotheruser");
        verify(passwordEncoder).matches("anotherpassword", "anotherEncodedPassword");
        verify(tokenService).generateToken(differentUser);
    }

    @Test
    @DisplayName("Should propagate exception when tokenService fails")
    void shouldPropagateExceptionWhenTokenServiceFails() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(existingUser)).thenThrow(new RuntimeException("Token generation failed"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginUserUseCase.execute(validLoginRequest));

        assertEquals("Token generation failed", exception.getMessage());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password123", "encodedPassword");
        verify(tokenService).generateToken(existingUser);
    }

    @Test
    @DisplayName("Should propagate exception when userRepository throws exception")
    void shouldPropagateExceptionWhenUserRepositoryThrowsException() {
        when(userRepository.findByUsername("testuser"))
                .thenThrow(new RuntimeException("Database connection failed"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginUserUseCase.execute(validLoginRequest));

        assertEquals("Database connection failed", exception.getMessage());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should propagate exception when passwordEncoder throws exception")
    void shouldPropagateExceptionWhenPasswordEncoderThrowsException() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword"))
                .thenThrow(new RuntimeException("Password encoding failed"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginUserUseCase.execute(validLoginRequest));

        assertEquals("Password encoding failed", exception.getMessage());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password123", "encodedPassword");
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should generate token with correct user")
    void shouldGenerateTokenWithCorrectUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(existingUser)).thenReturn("jwt-token");

        loginUserUseCase.execute(validLoginRequest);

        verify(tokenService).generateToken(argThat(user ->
                user.getId().equals(existingUser.getId()) &&
                        user.getUsername().equals(existingUser.getUsername()) &&
                        user.getEmail().equals(existingUser.getEmail())
        ));
    }
}