package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.affirmations.dto.IntentionDto;
import org.affirmations.service.IntentionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/intentions")
@RequiredArgsConstructor
public class IntentionControllerImpl implements IntentionController{
    private final IntentionService service;

    @Operation(summary = "Add an intention")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Override
    public IntentionDto save(@Valid @RequestBody IntentionDto intentionDto, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        return IntentionDto.fromModel(service.save(intentionDto, auth));
    }

    @Operation(summary = "Get intention history")
    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    @Override
    public List<IntentionDto> getHistory(@CurrentSecurityContext(expression = "authentication") Authentication auth) {
        return service.findByUser(auth).stream()
                .map(IntentionDto::fromModel)
                .collect(Collectors.toList());
    }

}