package org.affirmations.controller;

import org.affirmations.dto.AuthResponseDto;
import org.affirmations.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerImplTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthControllerImpl authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testRegister_Success() throws Exception {
        AuthResponseDto response = AuthResponseDto.builder().message("Registered successfully").build();
        when(authService.register(any(), any())).thenReturn(response);

        String json = "{\"username\":\"user1\",\"password\":\"pass1234\"}";

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registered successfully"));

        verify(authService, times(1)).register("user1", "pass1234");
    }

    @Test
    void testRegister_UsernameTaken() throws Exception {
        AuthResponseDto response = AuthResponseDto.builder().message("Username already taken").build();
        when(authService.register(any(), any())).thenReturn(response);

        String json = "{\"username\":\"user1\",\"password\":\"pass1234\"}";

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Username already taken"));
    }

    @Test
    void testLogin_Success() throws Exception {
        AuthResponseDto response = AuthResponseDto.builder().token("testtokenjwt").build();
        when(authService.login(any(), any())).thenReturn(response);

        String json = "{\"username\":\"user1\",\"password\":\"pass1234\"}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("testtokenjwt"));

        verify(authService, times(1)).login("user1", "pass1234");
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        AuthResponseDto response = AuthResponseDto.builder().message("Invalid credentials").build();
        when(authService.login(any(), any())).thenReturn(response);

        String json = "{\"username\":\"user1\",\"password\":\"wrongpass\"}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }
}