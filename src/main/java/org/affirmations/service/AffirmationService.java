package org.affirmations.service;

import org.affirmations.dto.AffirmationDto;
import org.affirmations.model.Affirmation;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AffirmationService {
    Affirmation findById(Long id);
    List<Affirmation> getAll();
    Affirmation getRandom();
    List<Affirmation> getByCategory(String category);
    List<Affirmation> getPersonalized(String username);
    Affirmation save(String username, AffirmationDto affirmationDto);
    Affirmation update(Long id, String username, AffirmationDto affirmationDto);
    String delete(Long id, Authentication auth);
}
