package org.affirmations.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.affirmations.model.Affirmation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyAffirmationServiceImpl implements DailyAffirmationService {

    private final AffirmationService service;
    private final SimpMessagingTemplate messagingTemplate;

    private DailyAffirmationMessage currentDailyAffirmation;

    @Value("${affirmations.daily.topic}")
    private String dailyTopic;

    private final Random random = new Random();

    @Scheduled(cron = "0 0 8 * * *")
    @Override
    public void sendDailyAffirmation() {
        List<Affirmation> affirmations = service.findAll();
        if (affirmations.isEmpty()) {
            log.warn("No affirmations found to send as daily affirmation.");
            return;
        }
        Affirmation selected = affirmations.get(random.nextInt(affirmations.size()));
        currentDailyAffirmation = new DailyAffirmationMessage(
                selected.getMessage(),
                selected.getCategory(),
                LocalDateTime.now().toString()
        );
        messagingTemplate.convertAndSend(dailyTopic, currentDailyAffirmation);
        log.info("Sent daily affirmation: {}", selected.getMessage());
    }

    // Call this on startup to initialize
    @PostConstruct
    @Override
    public void initDailyAffirmation() {
        if (currentDailyAffirmation == null) {
            List<Affirmation> affirmations = service.findAll();
            if (!affirmations.isEmpty()) {
                Affirmation selected = affirmations.get(random.nextInt(affirmations.size()));
                currentDailyAffirmation = new DailyAffirmationMessage(
                        selected.getMessage(),
                        selected.getCategory(),
                        LocalDateTime.now().toString()
                );
            }
        }
    }

    @Override
    public DailyAffirmationMessage getCurrentDailyAffirmation() {
        if (currentDailyAffirmation == null) {
            initDailyAffirmation();
        }
        return currentDailyAffirmation;
    }

    public record DailyAffirmationMessage(String message, String category, String timestamp) {}
}