package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.service.DailyAffirmationServiceImpl;

@Tag(name = "Daily Affirmations", description = "Endpoints for daily affirmation")
public interface DailyAffirmationController {
    DailyAffirmationServiceImpl.DailyAffirmationMessage getDailyAffirmation();
}
