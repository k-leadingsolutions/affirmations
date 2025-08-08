package org.affirmations.controller;

import org.affirmations.dto.IntentionDto;
import org.affirmations.security.JwtRequestFilter;
import org.affirmations.security.JwtUtil;
import org.affirmations.service.CustomUserDetailsService;
import org.affirmations.service.IntentionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IntentionControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class IntentionControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntentionService intentionService;

    @MockBean
    private Authentication authentication;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser // Mocks an authenticated user
    void save_shouldReturnSavedIntention() throws Exception {
        String testText = "Be positive";
        String testTimestamp = "2025-08-08";
        IntentionDto input = new IntentionDto(testText, testTimestamp);
        IntentionDto saved = new IntentionDto(testText, testTimestamp);

        Mockito.when(intentionService.save(any(IntentionDto.class), any(Authentication.class)))
                .thenReturn(IntentionDto.toModel(input)); // Or just `.thenReturn(saved)` if your controller returns DTO

        mockMvc.perform(post("/api/intentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "intentionText": "Be positive",
                              "date": "2025-08-08"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.intentionText").value("Be positive"))
                .andExpect(jsonPath("$.date").value("2025-08-08"));
    }

    @Test
    @WithMockUser
    void getHistory_shouldReturnListOfIntentions() throws Exception {
        String date1 = "2025-08-08";
        String date2 = "2025-08-07";
        IntentionDto dto1 = new IntentionDto("Be positive", date1);
        IntentionDto dto2 = new IntentionDto("Stay focused", date2);
        Mockito.when(intentionService.findByUser(any(Authentication.class)))
                .thenReturn(List.of(IntentionDto.toModel(dto1), IntentionDto.toModel(dto2)));

        mockMvc.perform(get("/api/intentions/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].intentionText").value("Be positive"))
                .andExpect(jsonPath("$[0].date").value("2025-08-08"))
                .andExpect(jsonPath("$[1].intentionText").value("Stay focused"))
                .andExpect(jsonPath("$[1].date").value("2025-08-07"));
    }
}