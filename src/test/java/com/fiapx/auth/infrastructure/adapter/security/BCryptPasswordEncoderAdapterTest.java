package com.fiapx.auth.infrastructure.adapter.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BCryptPasswordEncoderAdapterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private BCryptPasswordEncoderAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new BCryptPasswordEncoderAdapter(passwordEncoder);
    }

    @Test
    @DisplayName("Should encode password successfully")
    void shouldEncodePasswordSuccessfully() {
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = adapter.encode(rawPassword);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    @DisplayName("Should encode empty password")
    void shouldEncodeEmptyPassword() {
        String rawPassword = "";
        String encodedPassword = "$2a$10$encodedEmptyPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = adapter.encode(rawPassword);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    @DisplayName("Should encode null password")
    void shouldEncodeNullPassword() {
        String rawPassword = null;
        String encodedPassword = "$2a$10$encodedNullPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = adapter.encode(rawPassword);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    @DisplayName("Should verify password matches when true")
    void shouldMatchPasswordWhenTrue() {
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = adapter.matches(rawPassword, encodedPassword);

        assertTrue(result);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should verify password does not match when false")
    void shouldNotMatchPasswordWhenFalse() {
        String rawPassword = "wrongPassword";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = adapter.matches(rawPassword, encodedPassword);

        assertFalse(result);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should verify match with empty password")
    void shouldMatchWithEmptyPassword() {
        String rawPassword = "";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = adapter.matches(rawPassword, encodedPassword);

        assertFalse(result);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should verify match with null password")
    void shouldMatchWithNullPassword() {
        String rawPassword = null;
        String encodedPassword = "$2a$10$encodedPasswordHash";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = adapter.matches(rawPassword, encodedPassword);

        assertFalse(result);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should propagate exception from PasswordEncoder when encoding")
    void shouldPropagateExceptionFromPasswordEncoderWhenEncoding() {
        String rawPassword = "password123";

        when(passwordEncoder.encode(rawPassword)).thenThrow(new IllegalArgumentException("Invalid password"));

        assertThrows(IllegalArgumentException.class, () -> adapter.encode(rawPassword));

        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    @DisplayName("Should propagate exception from PasswordEncoder when matching")
    void shouldPropagateExceptionFromPasswordEncoderWhenMatching() {
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        when(passwordEncoder.matches(rawPassword, encodedPassword))
                .thenThrow(new IllegalArgumentException("Invalid encoded password"));

        assertThrows(IllegalArgumentException.class, () -> adapter.matches(rawPassword, encodedPassword));

        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should encode different passwords with different results")
    void shouldEncodeDifferentPasswordsWithDifferentResults() {
        String password1 = "password1";
        String password2 = "password2";
        String encoded1 = "$2a$10$encodedPassword1";
        String encoded2 = "$2a$10$encodedPassword2";

        when(passwordEncoder.encode(password1)).thenReturn(encoded1);
        when(passwordEncoder.encode(password2)).thenReturn(encoded2);

        String result1 = adapter.encode(password1);
        String result2 = adapter.encode(password2);

        assertEquals(encoded1, result1);
        assertEquals(encoded2, result2);
        assertNotEquals(result1, result2);

        verify(passwordEncoder).encode(password1);
        verify(passwordEncoder).encode(password2);
    }

    @Test
    @DisplayName("Should verify match with different combinations")
    void shouldMatchWithDifferentCombinations() {
        String rawPassword = "password123";
        String correctEncoded = "$2a$10$correctEncoded";
        String wrongEncoded = "$2a$10$wrongEncoded";

        when(passwordEncoder.matches(rawPassword, correctEncoded)).thenReturn(true);
        when(passwordEncoder.matches(rawPassword, wrongEncoded)).thenReturn(false);

        assertTrue(adapter.matches(rawPassword, correctEncoded));
        assertFalse(adapter.matches(rawPassword, wrongEncoded));

        verify(passwordEncoder).matches(rawPassword, correctEncoded);
        verify(passwordEncoder).matches(rawPassword, wrongEncoded);
    }

    @Test
    @DisplayName("Should handle null encoded password when matching")
    void shouldHandleNullEncodedPasswordWhenMatching() {
        String rawPassword = "password123";
        String encodedPassword = null;

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = adapter.matches(rawPassword, encodedPassword);

        assertFalse(result);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }
}
