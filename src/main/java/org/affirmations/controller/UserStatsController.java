package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@Tag(name = "Statistics", description = "Endpoints for managing user statistics")
public interface UserStatsController {

    Map<String, Object> getStats(Long id);
}
