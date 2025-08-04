package org.affirmations.service;

import org.affirmations.model.User;
import org.affirmations.repository.UserRepository;
import org.affirmations.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void register_returnsAlreadyTaken_ifUserExists() {
        String username = "existingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        String result = authService.register(username, "pass");

        assertEquals("Username already taken", result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_savesUser_ifUsernameIsFree() {
        String username = "newUser";
        String password = "plainPass";
        String encodedPassword = "encodedPass";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = authService.register(username, password);

        assertEquals("Registered successfully", result);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();
        assertEquals(username, savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
    }

    @Test
    void login_returnsToken_ifCredentialsAreValid() {
        String username = "user";
        String rawPassword = "pass";
        String encodedPassword = "encodedPass";
        String role = "USER";
        User user = new User(username, encodedPassword, role);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(username, role)).thenReturn("jwt-token");

        String result = authService.login(username, rawPassword);

        assertEquals("jwt-token", result);
    }

    @Test
    void login_returnsInvalidCredentials_ifUserNotFound() {
        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());

        String result = authService.login("nouser", "pass");

        assertEquals("Invalid credentials", result);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }

    @Test
    void login_returnsInvalidCredentials_ifPasswordDoesNotMatch() {
        String username = "user";
        String rawPassword = "wrongpass";
        String encodedPassword = "encodedPass";
        User user = new User(username, encodedPassword, "USER");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        String result = authService.login(username, rawPassword);

        assertEquals("Invalid credentials", result);
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }
}