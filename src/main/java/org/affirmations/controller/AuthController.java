package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.affirmations.dto.AuthRequestDto;
import org.affirmations.dto.AuthResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "Endpoints for managing authentication")
public interface AuthController {

    AuthResponseDto register(@RequestBody AuthRequestDto req);
    AuthResponseDto login(@RequestBody AuthRequestDto req);
}
