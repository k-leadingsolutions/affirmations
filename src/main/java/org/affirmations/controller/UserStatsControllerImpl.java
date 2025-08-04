package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.affirmations.service.UserStatsService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserStatsControllerImpl implements UserStatsController {

    private final UserStatsService userStatsService;

    public UserStatsControllerImpl(UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    @Operation(summary = "Get user statistics")
    @GetMapping("/{id}/stats")
    @Override
    public Map<String, Object> getStats(@PathVariable Long id) {
        return  userStatsService.getStats(id);
    }
}
