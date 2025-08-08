package org.affirmations.controller;

import org.affirmations.security.JwtRequestFilter;
import org.affirmations.security.JwtUtil;
import org.affirmations.service.CustomUserDetailsService;
import org.affirmations.service.UserStatsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserStatsControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class UserStatsControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStatsService userStatsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;


    @Test
    void getStats_shouldReturnUserStats() throws Exception {
        Long userId = 123L;
        Map<String, Object> stats = Map.of(
            "intentionsCount", 5,
            "affirmationsCount", 12
        );
        Mockito.when(userStatsService.getStats(eq(userId))).thenReturn(stats);

        mockMvc.perform(get("/api/users/{id}/stats", userId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.intentionsCount").value(5))
            .andExpect(jsonPath("$.affirmationsCount").value(12));
    }
}