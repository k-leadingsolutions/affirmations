package org.affirmations.service;

import org.affirmations.dto.AuthResponseDto;
import org.affirmations.model.User;
import org.affirmations.repository.UserRepository;
import org.affirmations.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void testRegister_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass1234")).thenReturn("encodedpass");

        AuthResponseDto response = authService.register("user1", "pass1234");
        assertEquals("Registered successfully", response.getMessage());
        assertNull(response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UsernameTaken() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(new User()));

        AuthResponseDto response = authService.register("user1", "pass1234");
        assertEquals("Username already taken", response.getMessage());
        assertNull(response.getToken());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        User user = new User("user1", "encodedpass", "USER");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass1234", "encodedpass")).thenReturn(true);
        when(jwtUtil.generateToken("user1", "USER")).thenReturn("testtokenjwt");

        AuthResponseDto response = authService.login("user1", "pass1234");
        assertEquals("testtokenjwt", response.getToken());
        assertNull(response.getMessage());
    }

    @Test
    void testLogin_InvalidCredentials_WrongUsername() {
        when(userRepository.findByUsername("baduser")).thenReturn(Optional.empty());

        AuthResponseDto response = authService.login("baduser", "pass1234");
        assertEquals("Invalid credentials", response.getMessage());
        assertNull(response.getToken());
    }

    @Test
    void testLogin_InvalidCredentials_WrongPassword() {
        User user = new User("user1", "encodedpass", "USER");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encodedpass")).thenReturn(false);

        AuthResponseDto response = authService.login("user1", "wrongpass");
        assertEquals("Invalid credentials", response.getMessage());
        assertNull(response.getToken());
    }
}