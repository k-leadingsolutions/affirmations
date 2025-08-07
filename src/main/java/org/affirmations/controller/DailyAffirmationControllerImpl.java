package org.affirmations.controller;

import lombok.RequiredArgsConstructor;
import org.affirmations.service.DailyAffirmationServiceImpl;
import org.affirmations.service.DailyAffirmationServiceImpl.DailyAffirmationMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DailyAffirmationControllerImpl implements DailyAffirmationController {

    private final DailyAffirmationServiceImpl dailyAffirmationServiceImpl;

    @GetMapping("/api/daily-affirmation")
    @Override
    public DailyAffirmationMessage getDailyAffirmation() {
        return dailyAffirmationServiceImpl.getCurrentDailyAffirmation();
    }
}