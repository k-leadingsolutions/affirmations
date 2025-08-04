package org.affirmations.service;

import org.affirmations.model.User;
import org.affirmations.repository.IntentionRepository;
import org.affirmations.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class UserStatsServiceImpl implements UserStatsService {
    private static final Logger logger = LoggerFactory.getLogger(UserStatsServiceImpl.class);

    private final UserRepository userRepository;
    private final IntentionRepository intentionRepository;

    public UserStatsServiceImpl(UserRepository userRepository, IntentionRepository intentionRepository) {
        this.userRepository = userRepository;
        this.intentionRepository = intentionRepository;
    }

    @Override
    public Map<String, Object> getStats(Long id) {
        logger.debug("Getting stats for user ID: {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            logger.warn("User not found for ID: {}", id);
            return Map.of("error", "User not found");
        }
        var intentions = intentionRepository.findByUser(user);
        logger.info("Found {} intentions for user '{}'", intentions.size(), user.getUsername());
        int streak = 0;
        // Calculate streak
        if (!intentions.isEmpty()) streak = 1;
        logger.debug("Calculated streak for user '{}': {}", user.getUsername(), streak);
        Map<String, Object> stats = Map.of(
                "username", user.getUsername(),
                "intentionsCount", intentions.size(),
                "currentStreak", streak
        );
        logger.info("Returning stats for user '{}': {}", user.getUsername(), stats);
        return stats;
    }
}