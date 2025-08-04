package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.affirmations.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register user")
    @PostMapping("/register")
    @Override
    public String register(@RequestParam String username, @RequestParam String password) {
        return authService.register(username, password);
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    @Override
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }
}
