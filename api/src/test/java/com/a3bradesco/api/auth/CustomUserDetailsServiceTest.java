package com.a3bradesco.api.auth;

import com.a3bradesco.api.config.security.CustomUserDetailsService;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.enums.UserRole;
import com.a3bradesco.api.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("João Silva");
        mockUser.setCpf("12345678900");
        mockUser.setEmail("joao@email.com");
        mockUser.setPassword("123456");
        mockUser.setUserRole(UserRole.ROLE_USER);
    }

    @Test
    void shouldLoadUserByEmail() {
        when(userRepository.findByEmail("joao@email.com")).thenReturn(mockUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername("joao@email.com");

        assertNotNull(userDetails);
        assertEquals("joao@email.com", userDetails.getUsername());
    }

    @Test
    void shouldLoadUserByCpf() {
        when(userRepository.findByCpf("12345678900")).thenReturn(mockUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername("12345678900");

        assertNotNull(userDetails);
        assertEquals("12345678900", userDetails.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByCpf("00000000000")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("00000000000");
        });
    }
}