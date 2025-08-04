package org.affirmations.service;

import org.affirmations.model.User;
import org.affirmations.model.Intention;
import org.affirmations.repository.UserRepository;
import org.affirmations.repository.IntentionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserStatsServiceImplTest {

    private UserRepository userRepository;
    private IntentionRepository intentionRepository;
    private UserStatsServiceImpl userStatsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        intentionRepository = mock(IntentionRepository.class);
        userStatsService = new UserStatsServiceImpl(userRepository, intentionRepository);
    }

    @Test
    void getStats_returnsErrorIfUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Map<String, Object> result = userStatsService.getStats(userId);

        assertEquals("User not found", result.get("error"));
        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository, intentionRepository);
    }

    @Test
    void getStats_returnsStatsWithZeroIntentionsAndStreakIfNoIntentions() {
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(intentionRepository.findByUser(user)).thenReturn(Collections.emptyList());

        Map<String, Object> result = userStatsService.getStats(userId);

        assertEquals("testuser", result.get("username"));
        assertEquals(0, result.get("intentionsCount"));
        assertEquals(0, result.get("currentStreak"));
        verify(userRepository).findById(userId);
        verify(intentionRepository).findByUser(user);
    }

    @Test
    void getStats_returnsStatsWithIntentionsAndStreakIfIntentionsExist() {
        Long userId = 3L;
        User user = new User();
        user.setId(userId);
        user.setUsername("intentuser");
        Intention intention1 = new Intention();
        Intention intention2 = new Intention();

        List<Intention> intentions = Arrays.asList(intention1, intention2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(intentionRepository.findByUser(user)).thenReturn(intentions);

        Map<String, Object> result = userStatsService.getStats(userId);

        assertEquals("intentuser", result.get("username"));
        assertEquals(2, result.get("intentionsCount"));
        assertEquals(1, result.get("currentStreak")); // streak is 1 if intentions exist
        verify(userRepository).findById(userId);
        verify(intentionRepository).findByUser(user);
    }
}