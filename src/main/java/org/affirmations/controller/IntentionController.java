package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.dto.IntentionDto;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Intentions", description = "Endpoints for managing intentions")
public interface IntentionController {

    IntentionDto save(IntentionDto intention, Authentication auth);

    List<IntentionDto> getHistory(Authentication auth);

}
