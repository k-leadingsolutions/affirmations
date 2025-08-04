package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.affirmations.dto.FeedbackDto;
import org.affirmations.dto.ReportDto;
import org.affirmations.service.FeedbackReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FeedbackReportControllerImpl implements FeedbackReportController {

    private final FeedbackReportService service;

    public FeedbackReportControllerImpl(FeedbackReportService service) {
        this.service = service;
    }

    @Operation(summary = "Submit feedback")
    @PostMapping("/feedback")
    @PreAuthorize("isAuthenticated()")
    @Override
    public String submitFeedback(@CurrentSecurityContext(expression = "authentication") Authentication auth, @RequestBody FeedbackDto feedback) {
        return service.submitFeedback(auth.getName(), feedback);
    }

    @Operation(summary = "Submit feedback report")
    @PostMapping("/report")
    @PreAuthorize("isAuthenticated()")
    @Override
    public String submitReport(@CurrentSecurityContext(expression = "authentication") Authentication auth, @RequestBody ReportDto report) {
        return service.submitReport(auth.getName(), report);
    }
}