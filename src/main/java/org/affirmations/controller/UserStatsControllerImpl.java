package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.affirmations.service.UserStatsService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserStatsControllerImpl implements UserStatsController {

    private final UserStatsService userStatsService;

    @Operation(summary = "Get user statistics")
    @GetMapping("/{id}/stats")
    @Override
    public Map<String, Object> getStats(@PathVariable Long id) {
        return  userStatsService.getStats(id);
    }
}
