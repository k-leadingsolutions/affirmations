package org.affirmations.service;

import org.affirmations.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(String username, String password);
    AuthResponseDto login(String username, String password);
}
