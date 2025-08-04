package org.affirmations.service;

import jakarta.transaction.Transactional;
import org.affirmations.dto.AffirmationDto;
import org.affirmations.model.Affirmation;
import org.affirmations.repository.AffirmationRepository;
import org.affirmations.util.AffirmationGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@Service
public class AffirmationServiceImpl implements AffirmationService {
    private static final Logger logger = LoggerFactory.getLogger(AffirmationServiceImpl.class);

    private final AffirmationRepository affirmationRepository;

    public AffirmationServiceImpl(AffirmationRepository affirmationRepository) {
        this.affirmationRepository = affirmationRepository;
    }

    @Override
    public Affirmation findById(Long id) {
        logger.debug("Finding affirmation by ID: {}", id);
        Affirmation affirmation = affirmationRepository.findById(id).orElse(null);
        if (affirmation != null) {
            logger.info("Affirmation found by ID: {}", id);
        } else {
            logger.warn("No affirmation found for ID: {}", id);
        }
        return affirmation;
    }

    @Transactional
    @Override
    public List<Affirmation> getAll() {
        logger.debug("Fetching all affirmations");
        affirmationRepository.saveAll(AffirmationGenerator.generatePositiveAffirmations("system"));
        List<Affirmation> affirmations = affirmationRepository.findAll();
        logger.info("Fetched {} affirmations", affirmations.size());
        return affirmations;
    }


    @Transactional
    @Override
    public Affirmation getRandom() {
        logger.debug("Fetching a random affirmation");
        affirmationRepository.saveAll(AffirmationGenerator.generatePositiveAffirmations("system"));
        List<Affirmation> affirmations = affirmationRepository.findAll();
        if (affirmations.isEmpty()) {
            logger.warn("No affirmations available for random selection");
            return null;
        }
        int idx = new Random().nextInt(affirmations.size());
        logger.info("Random affirmation selected: {}", affirmations.get(idx).getId());
        return affirmations.get(idx);
    }

    @Override
    public List<Affirmation> getByCategory(String category) {
        logger.debug("Fetching affirmations by category: {}", category);
        List<Affirmation> affirmations = affirmationRepository.findByCategory(category);
        logger.info("Found {} affirmations for category: {}", affirmations.size(), category);
        return affirmations;
    }

    @Override
    public List<Affirmation> getPersonalized(String username) {
        logger.debug("Fetching personalized affirmations for user: {}", username);
        List<Affirmation> affirmations = affirmationRepository.findBySubmittedBy(username);
            logger.info("Found {} affirmations submitted by user: {}", affirmations.size(), username);

        return affirmations;
    }

    @Transactional
    @Override
    public Affirmation save(String username, AffirmationDto affirmationDto) {
        logger.debug("Saving affirmation: {}", affirmationDto);
        Affirmation a = AffirmationDto.toModel(affirmationDto);
        Affirmation saved = affirmationRepository.save(a);
        logger.info("Affirmation saved with ID: {}", saved.getId());
        return saved;
    }

    @Transactional
    @Override
    public Affirmation update(Long id, String username, AffirmationDto affirmationDto) {
        logger.debug("Updating affirmation with ID: {}", id);
        return affirmationRepository.findById(id).map(a -> {
            a.setMessage(affirmationDto.getMessage());
            a.setCategory(affirmationDto.getCategory());
            a.setSubmittedBy(username);
            Affirmation updated = affirmationRepository.save(a);
            logger.info("Affirmation updated with ID: {}", updated.getId());
            return updated;
        }).orElseGet(() -> {
            logger.warn("Attempted to update non-existent affirmation with ID: {}", id);
            return null;
        });
    }

    @Override
    public String delete(Long id, Authentication auth) {
        logger.debug("Deleting affirmation with ID: {}", id);
        affirmationRepository.deleteById(id);
        logger.info("Affirmation deleted with ID: {}", id);
        return "Deleted";
    }
}