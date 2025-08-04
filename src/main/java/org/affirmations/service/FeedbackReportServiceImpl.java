package org.affirmations.service;

import jakarta.transaction.Transactional;
import org.affirmations.dto.FeedbackDto;
import org.affirmations.dto.ReportDto;
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
    public String submitFeedback(String username, FeedbackDto dto) {
        logger.debug("Submitting feedback from user: {}", username);
        dto.setUsername(username);
        dto.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        Feedback savedFeedback = feedbackRepository.save(FeedbackDto.toModel(dto));
        logger.info("Feedback submitted. ID: {} by user: {}", savedFeedback.getId(), username);
        return "Thank you for your feedback!";
    }

    @Override
    public String submitReport(String username, ReportDto dto) {
        logger.debug("Submitting report from user: {}", username);
        dto.setUsername(username);
        Report savedReport = reportRepository.save(ReportDto.toModel(dto));
        logger.info("Report submitted. ID: {} by user: {}", savedReport.getId(), username);
        return "Your report has been submitted.";
    }
}