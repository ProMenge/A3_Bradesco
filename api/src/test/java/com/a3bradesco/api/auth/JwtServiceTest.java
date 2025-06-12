package com.a3bradesco.api.auth;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.a3bradesco.api.config.security.jwt.JwtService;

import java.util.List;

class JwtServiceTest {

    private JwtService jwtService;

    private User userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        userDetails = new User(
            "testuser",
            "password",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtService.generateToken(userDetails, 123L);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(userDetails, 123L);
        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void testExtractUserId() {
        String token = jwtService.generateToken(userDetails, 123L);
        Long userId = jwtService.extractUserId(token);

        assertEquals(123L, userId);
    }

    @Test
    void testExtractExpiration() {
        String token = jwtService.generateToken(userDetails, 123L);
        Date expiration = jwtService.extractExpiration(token);

        assertTrue(expiration.after(new Date()));
    }
}