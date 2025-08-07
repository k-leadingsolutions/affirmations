package org.affirmations.service;

import org.affirmations.model.Affirmation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DailyAffirmationServiceImplTest {

    @Mock
    private AffirmationService affirmationService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private DailyAffirmationServiceImpl dailyAffirmationService;

    @Captor
    private ArgumentCaptor<DailyAffirmationServiceImpl.DailyAffirmationMessage> messageCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dailyAffirmationService = new DailyAffirmationServiceImpl(affirmationService, messagingTemplate);
        try {
            var field = DailyAffirmationServiceImpl.class.getDeclaredField("dailyTopic");
            field.setAccessible(true);
            field.set(dailyAffirmationService, "/topic/daily-affirmation");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sendDailyAffirmation_shouldSendAffirmation_whenAffirmationsExist() {
        Affirmation affirmation1 = new Affirmation();
        affirmation1.setMessage("You are strong!");
        affirmation1.setCategory("Strength");
        Affirmation affirmation2 = new Affirmation();
        affirmation2.setMessage("You are kind!");
        affirmation2.setCategory("Kindness");
        List<Affirmation> affirmations = Arrays.asList(affirmation1, affirmation2);

        when(affirmationService.findAll()).thenReturn(affirmations);

        dailyAffirmationService.sendDailyAffirmation();

        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/daily-affirmation"), any(DailyAffirmationServiceImpl.DailyAffirmationMessage.class));
        assertNotNull(dailyAffirmationService.getCurrentDailyAffirmation());
        String msg = dailyAffirmationService.getCurrentDailyAffirmation().message();
        assertTrue(msg.equals("You are strong!") || msg.equals("You are kind!"));
    }

    @Test
    void sendDailyAffirmation_shouldNotSend_whenNoAffirmationsExist() {
        when(affirmationService.findAll()).thenReturn(Collections.emptyList());

        dailyAffirmationService.sendDailyAffirmation();

        verify(messagingTemplate, never()).convertAndSend("testing", "testAgain");
        assertNull(dailyAffirmationService.getCurrentDailyAffirmation());
    }

    @Test
    void initDailyAffirmation_shouldInitializeIfNone() {
        Affirmation affirmation = new Affirmation();
        affirmation.setMessage("Be awesome!");
        affirmation.setCategory("Motivation");

        when(affirmationService.findAll()).thenReturn(List.of(affirmation));

        dailyAffirmationService.initDailyAffirmation();

        assertNotNull(dailyAffirmationService.getCurrentDailyAffirmation());
        assertEquals("Be awesome!", dailyAffirmationService.getCurrentDailyAffirmation().message());
        assertEquals("Motivation", dailyAffirmationService.getCurrentDailyAffirmation().category());
    }

    @Test
    void initDailyAffirmation_shouldDoNothingIfAlreadyInitialized() {
        Affirmation affirmation = new Affirmation();
        affirmation.setMessage("save!");
        affirmation.setCategory("Saved");

        when(affirmationService.findAll()).thenReturn(List.of(affirmation));
        dailyAffirmationService.initDailyAffirmation();

        var oldMessage = dailyAffirmationService.getCurrentDailyAffirmation();

        dailyAffirmationService.initDailyAffirmation();

        assertSame(oldMessage, dailyAffirmationService.getCurrentDailyAffirmation());
    }

    @Test
    void getCurrentDailyAffirmation_shouldInitIfNull() {
        Affirmation affirmation = new Affirmation();
        affirmation.setMessage("Smile!");
        affirmation.setCategory("Joy");

        when(affirmationService.findAll()).thenReturn(List.of(affirmation));

        assertNotNull(dailyAffirmationService.getCurrentDailyAffirmation());
        assertEquals("Smile!", dailyAffirmationService.getCurrentDailyAffirmation().message());
    }
}