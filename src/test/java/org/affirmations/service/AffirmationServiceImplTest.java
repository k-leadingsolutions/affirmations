package org.affirmations.service;

import org.affirmations.dto.AffirmationDto;
import org.affirmations.model.Affirmation;
import org.affirmations.repository.AffirmationRepository;
import org.affirmations.util.AffirmationGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AffirmationServiceImplTest {

    private AffirmationRepository affirmationRepository;
    private AffirmationServiceImpl affirmationService;

    @BeforeEach
    void setUp() {
        affirmationRepository = mock(AffirmationRepository.class);
        affirmationService = new AffirmationServiceImpl(affirmationRepository);
    }

    @Test
    void testFindById_found() {
        Affirmation affirmation = new Affirmation(1L, "You are awesome", "motivation", "user1");
        when(affirmationRepository.findById(1L)).thenReturn(Optional.of(affirmation));
        Affirmation result = affirmationService.findById(1L);
        assertNotNull(result);
        assertEquals("You are awesome", result.getMessage());
    }

    @Test
    void testFindById_notFound() {
        when(affirmationRepository.findById(2L)).thenReturn(Optional.empty());
        Affirmation result = affirmationService.findById(2L);
        assertNull(result);
    }

    @Test
    void testFindAll() {
        List<Affirmation> generated = AffirmationGenerator.generatePositiveAffirmations("system");
        when(affirmationRepository.findAll()).thenReturn(generated);

        List<Affirmation> result = affirmationService.findAll();

        verify(affirmationRepository).saveAll(anyList());
        verify(affirmationRepository).findAll();
        assertEquals(generated.size(), result.size());
    }

    @Test
    void testGetRandom() {
        List<Affirmation> affirmations = AffirmationGenerator.generatePositiveAffirmations("system");
        when(affirmationRepository.findAll()).thenReturn(affirmations);

        Affirmation result = affirmationService.getRandom();

        verify(affirmationRepository).saveAll(anyList());
        verify(affirmationRepository).findAll();
        assertNotNull(result);
        assertTrue(affirmations.contains(result));
    }

    @Test
    void testGetRandom_empty() {
        when(affirmationRepository.findAll()).thenReturn(List.of());
        Affirmation result = affirmationService.getRandom();
        assertNull(result);
    }

    @Test
    void testGetByCategory() {
        List<Affirmation> categoryList = Arrays.asList(
                new Affirmation(1L, "msg1", "motivation", "user"),
                new Affirmation(2L, "msg2", "motivation", "user")
        );
        when(affirmationRepository.findByCategory("motivation")).thenReturn(categoryList);

        List<Affirmation> result = affirmationService.getByCategory("motivation");
        verify(affirmationRepository).findByCategory("motivation");
        assertEquals(2, result.size());
    }

    @Test
    void testGetPersonalized() {
        List<Affirmation> personalized = Arrays.asList(
                new Affirmation(1L, "msg1", "motivation", "user1")
        );
        when(affirmationRepository.findBySubmittedBy("user1")).thenReturn(personalized);

        List<Affirmation> result = affirmationService.getPersonalized("user1");
        verify(affirmationRepository).findBySubmittedBy("user1");
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getSubmittedBy());
    }

    @Test
    void testSave() {
        AffirmationDto aff = new AffirmationDto(2L, "msg", "cat");
        Affirmation saved = new Affirmation(1L, "msg", "cat", "user");
        when(affirmationRepository.save(AffirmationDto.toModel(aff))).thenReturn(saved);

        Affirmation result = affirmationService.save("test", aff);
        verify(affirmationRepository).save(AffirmationDto.toModel(aff));
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate_found() {
        Affirmation existing = new Affirmation(1L, "old", "cat", "user");
        AffirmationDto updatedData = new AffirmationDto(3L, "new", "newcat");
        Affirmation updated = new Affirmation(1L, "new", "newcat", "user");
        Authentication auth = mock(Authentication.class);

        when(affirmationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(affirmationRepository.save(any(Affirmation.class))).thenReturn(updated);

        Affirmation result = affirmationService.update(1L, "test", updatedData);

        verify(affirmationRepository).findById(1L);
        verify(affirmationRepository).save(existing);
        assertEquals("new", result.getMessage());
        assertEquals("newcat", result.getCategory());
    }

    @Test
    void testUpdate_notFound() {
        AffirmationDto updatedData = new AffirmationDto(1L, "new", "newcat");
        Authentication auth = mock(Authentication.class);

        when(affirmationRepository.findById(1L)).thenReturn(Optional.empty());

        Affirmation result = affirmationService.update(1L, "testUser", updatedData);

        verify(affirmationRepository).findById(1L);
        verify(affirmationRepository, never()).save(any());
        assertNull(result);
    }

    @Test
    void testDelete() {
        Authentication auth = mock(Authentication.class);

        String result = affirmationService.delete(1L, auth);

        verify(affirmationRepository).deleteById(1L);
        assertEquals("Deleted", result);
    }
}