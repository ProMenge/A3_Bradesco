package com.a3bradesco.api.services;

import com.a3bradesco.api.dto.UserDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveNewUser_ShouldSave_WhenEmailAndCpfNotExist() {
        UserDTO dto = new UserDTO();
        dto.setName("Caue Urbini");
        dto.setEmail("caue@email.com");
        dto.setCpf("12345678901");
        dto.setPassword("Galinha7");


        when(userRepository.findByEmail(dto.getEmail())).thenReturn(null);
        when(userRepository.findByCpf(dto.getCpf())).thenReturn(null);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(dto.getName());
        savedUser.setEmail(dto.getEmail());
        savedUser.setCpf(dto.getCpf());
        savedUser.setPassword("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.saveNewUser(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void saveNewUser_ShouldThrow_WhenEmailOrCpfExists() {
        UserDTO dto = new UserDTO();
        dto.setName("Caue Urbini");
        dto.setEmail("caue@email.com");
        dto.setCpf("12345678901");
        dto.setPassword("Galinha7");


        when(userRepository.findByEmail(dto.getEmail())).thenReturn(new User());

        assertThrows(IllegalArgumentException.class, () -> userService.saveNewUser(dto));
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreValid() {
        String cpf = "12345678901";
        String rawPassword = "Galinha7";
        String encodedPassword = "encodedSenha";

        User user = new User();
        user.setCpf(cpf);
        user.setPassword(encodedPassword);

        when(userRepository.findByCpf(cpf)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        User result = userService.login(cpf, rawPassword);

        assertNotNull(result);
    }

    @Test
    void login_ShouldThrow_WhenInvalidCredentials() {
        when(userRepository.findByEmail("email@email.com")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
            userService.login("email@email.com", "wrongpassword")
        );
    }

    @Test
    void findById_ShouldReturnUser_WhenExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void findById_ShouldThrow_WhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void deleteUser_ShouldCallDeleteById_WhenUserExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
