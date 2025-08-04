package org.affirmations.service;

import org.affirmations.model.User;
import org.affirmations.repository.UserRepository;
import org.affirmations.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String register(String username, String password) {
        logger.debug("Attempting to register user: {}", username);
        if (userRepository.findByUsername(username).isPresent()) {
            logger.warn("Registration failed: Username '{}' already taken", username);
            return "Username already taken";
        }
        User user = new User(username, passwordEncoder.encode(password), "USER");
        userRepository.save(user);
        logger.info("User '{}' registered successfully", username);
        return "Registered successfully";
    }

    @Override
    public String login(String username, String password) {
        logger.debug("User '{}' attempting to login", username);
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            logger.info("User '{}' authenticated successfully", username);
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            logger.debug("JWT token generated for user '{}'", username);
            return token;
        }
        logger.warn("Login failed for user '{}': Invalid credentials", username);
        return "Invalid credentials";
    }
}