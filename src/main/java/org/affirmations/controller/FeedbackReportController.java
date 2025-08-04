package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.dto.FeedbackDto;
import org.affirmations.dto.ReportDto;
import org.springframework.security.core.Authentication;

@Tag(name = "Feedback", description = "Endpoints for managing feedback")
public interface FeedbackReportController {

    String submitFeedback(Authentication auth, FeedbackDto feedback);

    String submitReport(Authentication auth, ReportDto report);
}
