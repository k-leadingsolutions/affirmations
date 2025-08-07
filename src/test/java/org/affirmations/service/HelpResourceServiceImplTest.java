package org.affirmations.service;

import org.affirmations.model.HelpResource;
import org.affirmations.repository.HelpResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class HelpResourceServiceImplTest {

    private HelpResourceRepository helpResourceRepository;
    private HelpResourceServiceImpl helpResourceService;

    @BeforeEach
    void setUp() {
        helpResourceRepository = mock(HelpResourceRepository.class);
        helpResourceService = new HelpResourceServiceImpl(helpResourceRepository);
    }

    @Test
    void testFindByType_withType_callsFindByType() {
        String type = "video";
        List<HelpResource> expected = Arrays.asList(
                new HelpResource("Resource1", "video","test", "test"),
                new HelpResource("Resource1", "video","test", "test")
        );

        when(helpResourceRepository.findByType(type)).thenReturn(expected);

        List<HelpResource> result = helpResourceService.findByType(type);

        ArgumentCaptor<List<HelpResource>> captor = ArgumentCaptor.forClass(List.class);
        verify(helpResourceRepository).saveAll(captor.capture());
        assertNotNull(captor.getValue());

        verify(helpResourceRepository).findByType(type);
        assertEquals(expected, result);
    }

    @Test
    void testFindByType_withNullType_callsFindAll() {
        List<HelpResource> expected = Collections.singletonList(
                new HelpResource("Resource1", "article","test", "test")
        );

        when(helpResourceRepository.findAll()).thenReturn(expected);

        List<HelpResource> result = helpResourceService.findByType(null);

        //verify that saveAll is called
        verify(helpResourceRepository).saveAll(anyList());
        verify(helpResourceRepository).findAll();
        assertEquals(expected, result);
    }
}