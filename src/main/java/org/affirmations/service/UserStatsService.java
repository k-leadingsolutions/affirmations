package org.affirmations.service;

import java.util.Map;

public interface UserStatsService {
    Map<String, Object> getStats(Long id);
}
