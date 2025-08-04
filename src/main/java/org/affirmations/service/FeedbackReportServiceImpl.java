package org.affirmations.service;

import jakarta.transaction.Transactional;
import org.affirmations.model.Feedback;
import org.affirmations.model.Report;
import org.affirmations.repository.FeedbackRepository;
import org.affirmations.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@Service
public class FeedbackReportServiceImpl implements FeedbackReportService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackReportServiceImpl.class);

    private final FeedbackRepository feedbackRepository;
    private final ReportRepository reportRepository;

    public FeedbackReportServiceImpl(FeedbackRepository feedbackRepository, ReportRepository reportRepository) {
        this.feedbackRepository = feedbackRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public String submitFeedback(String username, Feedback feedback) {
        logger.debug("Submitting feedback from user: {}", username);
        feedback.setUsername(username);
        feedback.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        Feedback savedFeedback = feedbackRepository.save(feedback);
        logger.info("Feedback submitted. ID: {} by user: {}", savedFeedback.getId(), username);
        return "Thank you for your feedback!";
    }

    @Override
    public String submitReport(String username, Report report) {
        logger.debug("Submitting report from user: {}", username);
        report.setUsername(username);
        Report savedReport = reportRepository.save(report);
        logger.info("Report submitted. ID: {} by user: {}", savedReport.getId(), username);
        return "Your report has been submitted.";
    }
}