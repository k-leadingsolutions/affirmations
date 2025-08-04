package org.affirmations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints for managing authentication")
public interface AuthController {

    String register(String username, String password);

    String login(String username, String password);
}
