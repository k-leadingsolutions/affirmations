package org.affirmations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.affirmations.dto.AuthResponseDto;
import org.affirmations.model.User;
import org.affirmations.repository.UserRepository;
import org.affirmations.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDto register(String username, String password) {
        log.debug("Attempting to register user: {}", username);
        if (userRepository.findByUsername(username).isPresent()) {
            log.warn("Registration failed: Username '{}' already taken", username);
            return AuthResponseDto.builder()
                    .message("Username already taken")
                    .build();
        }
        User user = new User(username, passwordEncoder.encode(password), "USER");
        userRepository.save(user);
        log.info("User '{}' registered successfully", username);
        return AuthResponseDto.builder()
                .message("Registered successfully")
                .build();
    }

    @Override
    public AuthResponseDto login(String username, String password) {
        log.debug("User '{}' attempting to login", username);
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            log.info("User '{}' authenticated successfully", username);
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            log.debug("JWT token generated for user '{}'", username);
            return AuthResponseDto.builder()
                    .token(token)
                    .build();
        }
        log.warn("Login failed for user '{}': Invalid credentials", username);
        return AuthResponseDto.builder()
                .message("Invalid credentials")
                .build();
    }
}