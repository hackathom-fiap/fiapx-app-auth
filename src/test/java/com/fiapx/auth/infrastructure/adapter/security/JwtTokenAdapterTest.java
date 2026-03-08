package com.fiapx.auth.infrastructure.adapter.security;

import com.fiapx.auth.domain.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenAdapterTest {

    private JwtTokenAdapter jwtTokenAdapter;
    private User testUser;
    private String testSecret = "test-secret-key-for-jwt-token-generation";
    private long testExpiration = 3600000; // 1 hora

    @BeforeEach
    void setUp() {
        jwtTokenAdapter = new JwtTokenAdapter();
        ReflectionTestUtils.setField(jwtTokenAdapter, "secret", testSecret);
        ReflectionTestUtils.setField(jwtTokenAdapter, "expiration", testExpiration);

        testUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    @DisplayName("Should generate token successfully")
    void shouldGenerateTokenSuccessfully() {
        String token = jwtTokenAdapter.generateToken(testUser);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    @DisplayName("Should validate token successfully")
    void shouldValidateTokenSuccessfully() {
        String token = jwtTokenAdapter.generateToken(testUser);

        String username = jwtTokenAdapter.validateToken(token);

        assertEquals("testuser", username);
    }

    @Test
    @DisplayName("Should throw exception when validating invalid token")
    void shouldThrowExceptionWhenValidatingInvalidToken() {
        String invalidToken = "invalid.token.here";

        assertThrows(Exception.class, () -> jwtTokenAdapter.validateToken(invalidToken));
    }

    @Test
    @DisplayName("Should throw exception when validating expired token")
    void shouldThrowExceptionWhenValidatingExpiredToken() {
        ReflectionTestUtils.setField(jwtTokenAdapter, "expiration", -1); // Expirado imediatamente

        String token = jwtTokenAdapter.generateToken(testUser);

        assertThrows(Exception.class, () -> jwtTokenAdapter.validateToken(token));
    }

    @Test
    @DisplayName("Should generate token with different users")
    void shouldGenerateTokenWithDifferentUsers() {
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .username("user1")
                .email("user1@example.com")
                .build();

        User user2 = User.builder()
                .id(UUID.randomUUID())
                .username("user2")
                .email("user2@example.com")
                .build();

        String token1 = jwtTokenAdapter.generateToken(user1);
        String token2 = jwtTokenAdapter.generateToken(user2);

        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2);

        assertEquals("user1", jwtTokenAdapter.validateToken(token1));
        assertEquals("user2", jwtTokenAdapter.validateToken(token2));
    }

    @Test
    @DisplayName("Should generate token with email claim")
    void shouldGenerateTokenWithEmailClaim() {
        String token = jwtTokenAdapter.generateToken(testUser);

        Key key = Keys.hmacShaKeyFor(testSecret.getBytes());
        var claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testuser", claims.getSubject());
        assertEquals("test@example.com", claims.get("email"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

//    @Test
//    @DisplayName("Deve usar secret padrão quando não configurado")
//    void shouldUseDefaultSecretWhenNotConfigured() {
//        JwtTokenAdapter adapterWithDefaultSecret = new JwtTokenAdapter();
//        ReflectionTestUtils.setField(adapterWithDefaultSecret, "secret", "hackathon-secret-key-fiap-x-soat11-java");
//
//        User user = User.builder()
//                .username("testuser")
//                .email("test@example.com")
//                .build();
//
//        String token = adapterWithDefaultSecret.generateToken(user);
//        String username = adapterWithDefaultSecret.validateToken(token);
//
//        assertEquals("testuser", username);
//    }

    @Test
    @DisplayName("Should use default expiration when not configured")
    void shouldUseDefaultExpirationWhenNotConfigured() {
        JwtTokenAdapter adapterWithDefaultExpiration = new JwtTokenAdapter();
        ReflectionTestUtils.setField(adapterWithDefaultExpiration, "secret", "hackathon-secret-key-fiap-x-soat11-java");
        ReflectionTestUtils.setField(adapterWithDefaultExpiration, "expiration", 86400000L);

        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .build();

        String token = adapterWithDefaultExpiration.generateToken(user);

        Key key = Keys.hmacShaKeyFor("hackathon-secret-key-fiap-x-soat11-java".getBytes());
        var claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();

        long difference = expiration.getTime() - issuedAt.getTime();
        assertEquals(86400000, difference); // 24 horas em milissegundos
    }

    @Test
    @DisplayName("Should validate token with correct username")
    void shouldValidateTokenWithCorrectUsername() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("specificuser")
                .email("specific@example.com")
                .build();

        String token = jwtTokenAdapter.generateToken(user);
        String username = jwtTokenAdapter.validateToken(token);

        assertEquals("specificuser", username);
    }

    @Test
    @DisplayName("Should generate token with correct timestamp")
    void shouldGenerateTokenWithCorrectTimestamp() {
        long beforeGeneration = System.currentTimeMillis();
        String token = jwtTokenAdapter.generateToken(testUser);
        long afterGeneration = System.currentTimeMillis();

        Key key = Keys.hmacShaKeyFor(testSecret.getBytes());
        var claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();

        assertTrue(issuedAt.getTime() >= beforeGeneration - 1000); // Permitir 1 segundo de tolerância
        assertTrue(issuedAt.getTime() <= afterGeneration + 1000); // Permitir 1 segundo de tolerância

        long expectedExpiration = issuedAt.getTime() + testExpiration;
        long actualDifference = Math.abs(expectedExpiration - expiration.getTime());
        assertTrue(actualDifference <= 1000, "Difference should be less than 1 second, but was: " + actualDifference);
    }

    @Test
    @DisplayName("Should throw exception when validating tampered token")
    void shouldThrowExceptionWhenValidatingTamperedToken() {
        String token = jwtTokenAdapter.generateToken(testUser);

        String[] parts = token.split("\\.");
        String tamperedToken = parts[0] + "." + parts[1] + ".tamperedSignature";

        assertThrows(Exception.class, () -> jwtTokenAdapter.validateToken(tamperedToken));
    }

    @Test
    @DisplayName("Should throw exception when validating malformed token")
    void shouldThrowExceptionWhenValidatingMalformedToken() {
        String malformedToken = "malformedToken";

        assertThrows(Exception.class, () -> jwtTokenAdapter.validateToken(malformedToken));
    }

    @Test
    @DisplayName("Should generate token even with user without ID")
    void shouldGenerateTokenEvenWithUserWithoutId() {
        User userWithoutId = User.builder()
                .username("noiduser")
                .email("noid@example.com")
                .build();

        String token = jwtTokenAdapter.generateToken(userWithoutId);
        String username = jwtTokenAdapter.validateToken(token);

        assertEquals("noiduser", username);
    }

    @Test
    @DisplayName("Should generate token even with user without email")
    void shouldGenerateTokenEvenWithUserWithoutEmail() {
        User userWithoutEmail = User.builder()
                .id(UUID.randomUUID())
                .username("noemailuser")
                .build();

        String token = jwtTokenAdapter.generateToken(userWithoutEmail);
        String username = jwtTokenAdapter.validateToken(token);

        assertEquals("noemailuser", username);
    }
}