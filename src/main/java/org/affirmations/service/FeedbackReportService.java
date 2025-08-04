package org.affirmations.service;

import org.affirmations.model.Feedback;
import org.affirmations.model.Report;

public interface FeedbackReportService {
    String submitFeedback(String username, Feedback feedback);
    String submitReport(String username, Report report);
}
