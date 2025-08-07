package org.affirmations.controller;

import org.affirmations.model.Affirmation;
import org.affirmations.repository.AffirmationRepository;
import org.affirmations.security.JwtRequestFilter;
import org.affirmations.security.JwtUtil;
import org.affirmations.service.AffirmationService;
import org.affirmations.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(org.affirmations.controller.AffirmationControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class AffirmationControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AffirmationService affirmationService;

    @MockBean
    private AffirmationRepository affirmationRepository;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    private List<Affirmation> affirmationList;

    @BeforeEach
    void setUp() {

        affirmationList = Arrays.asList(
                new Affirmation(1L, "You are strong", "general", "user1"),
                new Affirmation(2L, "Take care of your mind", "mental health", "user2")
        );

        Mockito.when(affirmationService.findAll()).thenReturn(affirmationList);
        Mockito.when(affirmationService.getByCategory("general")).thenReturn(
                Collections.singletonList(affirmationList.get(0))
        );

        Mockito.when(affirmationService.getPersonalized("user1"))
                .thenReturn(Collections.singletonList(affirmationList.get(0)));

    }

    @Test
    void getAllShouldReturnAffirmations() throws Exception {
        mockMvc.perform(get("/api/affirmations")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].message", is("You are strong")));
    }

    @Test
    void getByCategoryShouldReturnAffirmations() throws Exception {
        mockMvc.perform(get("/api/affirmations/category/general"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category", is("general")));
    }

    @Test
    void getPersonalizedShouldReturnAffirmationsForUser() throws Exception {
        mockMvc.perform(get("/api/affirmations/personalized?username=user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message", is("You are strong")));
    }
}