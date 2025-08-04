package org.affirmations.controller;

import org.affirmations.model.Feedback;
import org.affirmations.model.Report;
import org.affirmations.service.FeedbackReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackReportControllerImplTest {

    private FeedbackReportService service;
    private FeedbackReportControllerImpl controller;
    private Authentication auth;

    @BeforeEach
    void setUp() {
        service = mock(FeedbackReportService.class);
        controller = new FeedbackReportControllerImpl(service);
        auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
    }

    @Test
    void testSubmitFeedback() {
        Feedback feedback = new Feedback();
        when(service.submitFeedback(eq("testuser"), eq(feedback))).thenReturn("Thank you for your feedback!");

        String result = controller.submitFeedback(auth, feedback);

        verify(service, times(1)).submitFeedback("testuser", feedback);
        assertEquals("Thank you for your feedback!", result);
    }

    @Test
    void testSubmitReport() {
        Report report = new Report();
        when(service.submitReport(eq("testuser"), eq(report))).thenReturn("Your report has been submitted.");

        String result = controller.submitReport(auth, report);

        verify(service, times(1)).submitReport("testuser", report);
        assertEquals("Your report has been submitted.", result);
    }
}