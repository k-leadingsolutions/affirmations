package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.dto.AffirmationDto;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Affirmations", description = "Endpoints for managing affirmations")
public interface AffirmationController {

    List<AffirmationDto> getAll();

    AffirmationDto getRandom();

    List<AffirmationDto> getByCategory(String category);

    List<AffirmationDto> getPersonalized(String username);

    AffirmationDto save(AffirmationDto affirmation, Authentication auth);

    AffirmationDto update(Long id, AffirmationDto affirmation, Authentication auth);

    String delete(Long id, Authentication auth);
}