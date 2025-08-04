package org.affirmations.service;

import org.affirmations.dto.FeedbackDto;
import org.affirmations.dto.ReportDto;

public interface FeedbackReportService {
    String submitFeedback(String username, FeedbackDto feedback);
    String submitReport(String username, ReportDto report);
}
