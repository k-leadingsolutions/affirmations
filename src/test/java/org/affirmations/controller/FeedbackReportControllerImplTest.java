package org.affirmations.controller;

import org.affirmations.dto.FeedbackDto;
import org.affirmations.dto.ReportDto;
import org.affirmations.service.FeedbackReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        FeedbackDto dto = new FeedbackDto();
        when(service.submitFeedback(eq("testuser"), eq(dto))).thenReturn("Thank you for your feedback!");

        String result = controller.submitFeedback(auth, dto);

        verify(service, times(1)).submitFeedback("testuser", dto);
        assertEquals("Thank you for your feedback!", result);
    }

    @Test
    void testSubmitReport() {
        ReportDto dto = new ReportDto();
        when(service.submitReport(eq("testuser"), eq(dto))).thenReturn("Your report has been submitted.");

        String result = controller.submitReport(auth, dto);

        verify(service, times(1)).submitReport("testuser", dto);
        assertEquals("Your report has been submitted.", result);
    }
}