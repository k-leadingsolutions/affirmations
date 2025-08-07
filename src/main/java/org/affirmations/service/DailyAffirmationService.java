package org.affirmations.service;

import org.affirmations.service.DailyAffirmationServiceImpl.DailyAffirmationMessage;

public interface DailyAffirmationService {
    void sendDailyAffirmation();
    void initDailyAffirmation();
    DailyAffirmationMessage getCurrentDailyAffirmation();
}