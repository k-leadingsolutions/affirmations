package org.affirmations.service;

import org.affirmations.dto.IntentionDto;
import org.affirmations.model.Intention;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IntentionService {
    Intention save(IntentionDto intentionDto, Authentication auth);
    List<Intention> getHistory(Authentication auth);
    List<Intention> findByUser(Authentication auth);

}
