package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.affirmations.dto.AuthRequestDto;
import org.affirmations.dto.AuthResponseDto;
import org.affirmations.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Operation(summary = "Register user")
    @PostMapping("/register")
    @Override
    public AuthResponseDto register(@RequestBody AuthRequestDto req) {
        return authService.register(req.getUsername(), req.getPassword());
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    @Override
    public AuthResponseDto login(@RequestBody AuthRequestDto req) {
        return authService.login(req.getUsername(), req.getPassword());
    }
}