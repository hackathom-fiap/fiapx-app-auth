package com.fiapx.auth.application.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationRequestEqualsHashCodeTest {

    @Test
    @DisplayName("Should verify equals with null fields")
    void shouldVerifyEqualsWithNullFields() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        UserRegistrationRequest request2 = new UserRegistrationRequest();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with mixed null fields")
    void shouldVerifyEqualsWithMixedNullFields() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("user");
        request1.setEmail("user@example.com");
        request1.setPassword("pass");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("user");
        request2.setEmail(null);
        request2.setPassword(null);

        UserRegistrationRequest request3 = new UserRegistrationRequest();
        request3.setUsername(null);
        request3.setEmail("user@example.com");
        request3.setPassword("pass");

        assertNotEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request2, request3);
    }

    @Test
    @DisplayName("Should verify equals with different username only")
    void shouldVerifyEqualsWithDifferentUsernameOnly() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("user1");
        request1.setEmail("test@example.com");
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("user2");
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different email only")
    void shouldVerifyEqualsWithDifferentEmailOnly() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail("email1@example.com");
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("email2@example.com");
        request2.setPassword("password123");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with different password only")
    void shouldVerifyEqualsWithDifferentPasswordOnly() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail("test@example.com");
        request1.setPassword("password1");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword("password2");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null username")
    void shouldVerifyEqualsWithNullUsername() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername(null);
        request1.setEmail("test@example.com");
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null email")
    void shouldVerifyEqualsWithNullEmail() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail(null);
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with null password")
    void shouldVerifyEqualsWithNullPassword() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail("test@example.com");
        request1.setPassword(null);

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null usernames")
    void shouldVerifyEqualsWithBothNullUsernames() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername(null);
        request1.setEmail("test@example.com");
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername(null);
        request2.setEmail("test@example.com");
        request2.setPassword("password123");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null emails")
    void shouldVerifyEqualsWithBothNullEmails() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail(null);
        request1.setPassword("password123");

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail(null);
        request2.setPassword("password123");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with both null passwords")
    void shouldVerifyEqualsWithBothNullPasswords() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        request1.setUsername("testuser");
        request1.setEmail("test@example.com");
        request1.setPassword(null);

        UserRegistrationRequest request2 = new UserRegistrationRequest();
        request2.setUsername("testuser");
        request2.setEmail("test@example.com");
        request2.setPassword(null);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Should verify equals with all null fields")
    void shouldVerifyEqualsWithAllNullFields() {
        UserRegistrationRequest request1 = new UserRegistrationRequest();
        UserRegistrationRequest request2 = new UserRegistrationRequest();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
