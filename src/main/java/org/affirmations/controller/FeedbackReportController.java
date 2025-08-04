package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.model.Feedback;
import org.affirmations.model.Report;
import org.springframework.security.core.Authentication;

@Tag(name = "Feedback", description = "Endpoints for managing feedback")
public interface FeedbackReportController {

    String submitFeedback(Authentication auth, Feedback feedback);

    String submitReport(Authentication auth, Report report);
}
