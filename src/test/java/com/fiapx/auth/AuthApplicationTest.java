package com.fiapx.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthApplicationTest {

    @Test
    @DisplayName("Should load application context")
    void shouldLoadApplicationContext() {
        // This test verifies that the Spring application context loads successfully
        // If there are any configuration issues, this test will fail
        // The @SpringBootTest annotation ensures the context is loaded before this test runs
        // No additional assertions needed - reaching this point means context loaded successfully
        assertTrue(true, "Application context loaded successfully");
    }

    @Test
    @DisplayName("Should start application without errors")
    void shouldStartApplicationWithoutErrors() {
        // This test ensures the application can start properly
        // The @SpringBootTest annotation handles the actual startup
        // If the application fails to start, the test will fail before reaching this point
        assertTrue(true, "Application started without errors");
    }
}
