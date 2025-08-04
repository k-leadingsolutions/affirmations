package org.affirmations.service;

import jakarta.transaction.Transactional;
import org.affirmations.dto.IntentionDto;
import org.affirmations.model.Intention;
import org.affirmations.model.User;
import org.affirmations.repository.IntentionRepository;
import org.affirmations.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class IntentionServiceImpl implements IntentionService {
    private static final Logger logger = LoggerFactory.getLogger(IntentionServiceImpl.class);

    private final UserRepository userRepository;
    private final IntentionRepository intentionRepository;

    public IntentionServiceImpl(UserRepository userRepository, IntentionRepository intentionRepository) {
        this.userRepository = userRepository;
        this.intentionRepository = intentionRepository;
    }

    @Transactional
    @Override
    public Intention save(IntentionDto intentionDto, Authentication auth) {
        String username = auth.getName();
        logger.debug("Attempting to save intention for user: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow();
        Intention i = IntentionDto.toModel(intentionDto);
        i.setUser(user);
        Intention saved = intentionRepository.save(i);
        logger.info("Intention saved for user '{}': {}", username, saved.getId());
        return saved;
    }

    @Override
    public List<Intention> getHistory(Authentication auth) {
        String username = auth.getName();
        logger.debug("Fetching intention history for user: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Intention> intentions = intentionRepository.findByUser(user);
        logger.info("Fetched {} intentions for user '{}'", intentions.size(), username);
        return intentions;
    }

    @Override
    public List<Intention> findByUser(Authentication auth) {
        String username = auth.getName();
        logger.debug("Finding intentions by user: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Intention> intentions = intentionRepository.findByUser(user);
        logger.info("Found {} intentions for user '{}'", intentions.size(), username);
        return intentions;
    }
}