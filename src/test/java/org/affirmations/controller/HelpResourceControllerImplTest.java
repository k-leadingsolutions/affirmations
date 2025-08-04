package org.affirmations.controller;

import org.affirmations.model.HelpResource;
import org.affirmations.security.JwtRequestFilter;
import org.affirmations.security.JwtUtil;
import org.affirmations.service.CustomUserDetailsService;
import org.affirmations.service.HelpResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelpResourceControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class HelpResourceControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelpResourceService service;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void getAll_returnsListOfResources_whenTypeIsProvided() throws Exception {
        HelpResource resource = new HelpResource();
        resource.setId(1L);
        resource.setType("video");
        resource.setDescription("How to Use the App");

        when(service.findByType("video")).thenReturn(List.of(resource));

        mockMvc.perform(get("/api/help/resources")
                        .param("type", "video")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("video"))
                .andExpect(jsonPath("$[0].description").value("How to Use the App"));

        verify(service).findByType("video");
    }

    @Test
    void getAll_returnsListOfResources_whenTypeIsNotProvided() throws Exception {
        HelpResource resource = new HelpResource();
        resource.setId(2L);
        resource.setType("article");
        resource.setDescription("Getting Started");

        when(service.findByType(null)).thenReturn(List.of(resource));

        mockMvc.perform(get("/api/help/resources")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("article"))
                .andExpect(jsonPath("$[0].description").value("Getting Started"));

        verify(service).findByType(null);
    }
}