package org.affirmations.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.affirmations.dto.AffirmationDto;
import org.affirmations.model.Affirmation;
import org.affirmations.repository.AffirmationRepository;
import org.affirmations.util.AffirmationGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AffirmationServiceImpl implements AffirmationService {

    private final AffirmationRepository affirmationRepository;

    @Override
    public Affirmation findById(Long id) {
        log.debug("Finding affirmation by ID: {}", id);
        Affirmation affirmation = affirmationRepository.findById(id).orElse(null);
        if (affirmation != null) {
            log.info("Affirmation found by ID: {}", id);
        } else {
            log.warn("No affirmation found for ID: {}", id);
        }
        return affirmation;
    }

    @Transactional
    @Override
    public List<Affirmation> findAll() {
        log.debug("Fetching all affirmations");
        affirmationRepository.saveAll(AffirmationGenerator.generatePositiveAffirmations("system"));
        List<Affirmation> affirmations = affirmationRepository.findAll();
        log.info("Fetched {} affirmations", affirmations.size());
        return affirmations;
    }


    @Transactional
    @Override
    public Affirmation getRandom() {
        log.debug("Fetching a random affirmation");
        affirmationRepository.saveAll(AffirmationGenerator.generatePositiveAffirmations("system"));
        List<Affirmation> affirmations = affirmationRepository.findAll();
        if (affirmations.isEmpty()) {
            log.warn("No affirmations available for random selection");
            return null;
        }
        int idx = new Random().nextInt(affirmations.size());
        log.info("Random affirmation selected: {}", affirmations.get(idx).getId());
        return affirmations.get(idx);
    }

    @Override
    public List<Affirmation> getByCategory(String category) {
        log.debug("Fetching affirmations by category: {}", category);
        List<Affirmation> affirmations = affirmationRepository.findByCategory(category);
        log.info("Found {} affirmations for category: {}", affirmations.size(), category);
        return affirmations;
    }

    @Override
    public List<Affirmation> getPersonalized(String username) {
        log.debug("Fetching personalized affirmations for user: {}", username);
        List<Affirmation> affirmations = affirmationRepository.findBySubmittedBy(username);
            log.info("Found {} affirmations submitted by user: {}", affirmations.size(), username);

        return affirmations;
    }

    @Transactional
    @Override
    public Affirmation save(String username, AffirmationDto affirmationDto) {
        log.debug("Saving affirmation: {}", affirmationDto);
        Affirmation a = AffirmationDto.toModel(affirmationDto);
        Affirmation saved = affirmationRepository.save(a);
        log.info("Affirmation saved with ID: {}", saved.getId());
        return saved;
    }

    @Transactional
    @Override
    public Affirmation update(Long id, String username, AffirmationDto affirmationDto) {
        log.debug("Updating affirmation with ID: {}", id);
        return affirmationRepository.findById(id).map(a -> {
            a.setMessage(affirmationDto.getMessage());
            a.setCategory(affirmationDto.getCategory());
            a.setSubmittedBy(username);
            Affirmation updated = affirmationRepository.save(a);
            log.info("Affirmation updated with ID: {}", updated.getId());
            return updated;
        }).orElseGet(() -> {
            log.warn("Attempted to update non-existent affirmation with ID: {}", id);
            return null;
        });
    }

    @Override
    public String delete(Long id, Authentication auth) {
        log.debug("Deleting affirmation with ID: {}", id);
        affirmationRepository.deleteById(id);
        log.info("Affirmation deleted with ID: {}", id);
        return "Deleted";
    }
}