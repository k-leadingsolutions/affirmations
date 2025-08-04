package org.affirmations.controller;

import org.affirmations.security.JwtRequestFilter;
import org.affirmations.security.JwtUtil;
import org.affirmations.service.AuthService;
import org.affirmations.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void registerShouldReturnSuccess() throws Exception {
        Mockito.when(authService.register(eq("user123"), eq("pass"))).thenReturn("Registered successfully");

        mockMvc.perform(post("/api/auth/register")
                        .param("username", "user123")
                        .param("password", "pass")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Registered successfully"));
    }

    @Test
    void registerShouldReturnErrorIfUserExists() throws Exception {
        Mockito.when(authService.register(eq("existing"), eq("pass"))).thenReturn("Username already taken");

        mockMvc.perform(post("/api/auth/register")
                        .param("username", "existing")
                        .param("password", "pass")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Username already taken"));
    }

    @Test
    void loginShouldReturnToken() throws Exception {
        Mockito.when(authService.login(eq("user"), eq("secret"))).thenReturn("token123");

        mockMvc.perform(post("/api/auth/login")
                        .param("username", "user")
                        .param("password", "secret")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("token123"));
    }

    @Test
    void loginShouldReturnInvalidCredentials() throws Exception {
        Mockito.when(authService.login(eq("nouser"), eq("nopass"))).thenReturn("Invalid credentials");

        mockMvc.perform(post("/api/auth/login")
                        .param("username", "nouser")
                        .param("password", "nopass")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid credentials"));
    }
}