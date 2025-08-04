package org.affirmations.service;

import org.affirmations.dto.IntentionDto;
import org.affirmations.model.Intention;
import org.affirmations.model.User;
import org.affirmations.repository.IntentionRepository;
import org.affirmations.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IntentionServiceImplTest {

    private UserRepository userRepository;
    private IntentionRepository intentionRepository;
    private IntentionServiceImpl intentionService;
    private Authentication auth;
    private User user;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        intentionRepository = mock(IntentionRepository.class);
        intentionService = new IntentionServiceImpl(userRepository, intentionRepository);
        auth = mock(Authentication.class);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(auth.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
    }

    @Test
    void save_setsUserAndSavesIntention() {
        IntentionDto intention = new IntentionDto();
        intention.setIntentionText("Intent Text");

        Intention savedIntention = new Intention();
        savedIntention.setId(42L);
        savedIntention.setIntentionText("Intent Text");
        savedIntention.setUser(user);

        when(intentionRepository.save(any(Intention.class))).thenReturn(savedIntention);

        Intention result = intentionService.save(intention, auth);

        assertEquals(savedIntention, result);
        assertEquals(user, result.getUser());

        ArgumentCaptor<Intention> captor = ArgumentCaptor.forClass(Intention.class);
        verify(intentionRepository).save(captor.capture());
        assertEquals(user, captor.getValue().getUser());
    }

    @Test
    void getHistory_returnsIntentionsForUser() {
        Intention intention = new Intention();
        intention.setId(3L);
        intention.setUser(user);

        when(intentionRepository.findByUser(user)).thenReturn(List.of(intention));

        List<Intention> result = intentionService.getHistory(auth);

        assertEquals(1, result.size());
        assertEquals(intention, result.get(0));
    }

    @Test
    void findByUser_returnsIntentionsForUser() {
        Intention intention = new Intention();
        intention.setUser(user);

        when(intentionRepository.findByUser(user)).thenReturn(Collections.singletonList(intention));

        List<Intention> result = intentionService.findByUser(auth);

        assertEquals(1, result.size());
        assertEquals(intention, result.get(0));
    }
}