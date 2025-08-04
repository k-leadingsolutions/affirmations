package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.dto.HelpResourceDto;

import java.util.List;

@Tag(name = "Help", description = "Endpoints for managing help resources")
public interface HelpResourceController {

    List<HelpResourceDto> getAll(String type);
}
