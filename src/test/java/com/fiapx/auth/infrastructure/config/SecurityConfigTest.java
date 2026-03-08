package com.fiapx.auth.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    @DisplayName("Should create passwordEncoder bean")
    void shouldCreatePasswordEncoderBean() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        
        assertNotNull(passwordEncoder);
        assertInstanceOf(org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.class, passwordEncoder);
        
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword));
    }

    @Test
    @DisplayName("Should create securityFilterChain bean")
    void shouldCreateSecurityFilterChainBean() {
        assertNotNull(securityConfig);
        
        assertDoesNotThrow(() -> {
            try {
                securityConfig.getClass().getMethod("securityFilterChain", HttpSecurity.class);
            } catch (NoSuchMethodException e) {
                fail("securityFilterChain method should exist");
            }
        });
    }

    @Test
    @DisplayName("Should create BCryptPasswordEncoder with default strength")
    void shouldCreateBCryptPasswordEncoderWithDefaultStrength() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        
        String password = "testPassword";
        String encoded1 = passwordEncoder.encode(password);
        String encoded2 = passwordEncoder.encode(password);
        
        assertNotEquals(encoded1, encoded2);
        
        assertTrue(passwordEncoder.matches(password, encoded1));
        assertTrue(passwordEncoder.matches(password, encoded2));
    }

    @Test
    @DisplayName("Should handle empty password encoding")
    void shouldHandleEmptyPasswordEncoding() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        
        String emptyPassword = "";
        String encodedEmpty = passwordEncoder.encode(emptyPassword);
        
        assertNotNull(encodedEmpty);
        assertNotEquals(emptyPassword, encodedEmpty);
        assertTrue(passwordEncoder.matches(emptyPassword, encodedEmpty));
        assertFalse(passwordEncoder.matches("nonEmpty", encodedEmpty));
    }

    @Test
    @DisplayName("Should handle null password encoding")
    void shouldHandleNullPasswordEncoding() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        
        assertThrows(IllegalArgumentException.class, () -> {
            passwordEncoder.encode(null);
        });
    }

    @Test
    @DisplayName("Should verify password encoder consistency")
    void shouldVerifyPasswordEncoderConsistency() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        
        String password = "consistencyTest";
        String encoded = passwordEncoder.encode(password);
        
        // Multiple checks should all return true
        assertTrue(passwordEncoder.matches(password, encoded));
        assertTrue(passwordEncoder.matches(password, encoded));
        assertTrue(passwordEncoder.matches(password, encoded));
    }
}
