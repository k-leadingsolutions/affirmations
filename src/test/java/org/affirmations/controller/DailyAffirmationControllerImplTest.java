package org.affirmations.controller;

import org.affirmations.security.JwtRequestFilter;
import org.affirmations.security.JwtUtil;
import org.affirmations.service.CustomUserDetailsService;
import org.affirmations.service.DailyAffirmationServiceImpl;
import org.affirmations.service.DailyAffirmationServiceImpl.DailyAffirmationMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DailyAffirmationControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class DailyAffirmationControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DailyAffirmationServiceImpl dailyAffirmationServiceImpl;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void getDailyAffirmation_returnsAffirmationMessage() throws Exception {
        DailyAffirmationMessage message = new DailyAffirmationMessage("You are capable and strong!","motivation",  LocalDateTime.now().toString());

        when(dailyAffirmationServiceImpl.getCurrentDailyAffirmation()).thenReturn(message);

        mockMvc.perform(get("/api/daily-affirmation")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("You are capable and strong!"));
    }
}