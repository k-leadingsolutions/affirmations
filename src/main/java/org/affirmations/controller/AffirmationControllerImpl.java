package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.affirmations.dto.AffirmationDto;
import org.affirmations.service.AffirmationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/affirmations")
@RequiredArgsConstructor
public class AffirmationControllerImpl {

    private final AffirmationService service;

    @Operation(summary = "Get all affirmations")
    @GetMapping
    public List<AffirmationDto> getAll() {
        return service.findAll().stream()
                .map(AffirmationDto::fromModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get random affirmation")
    @GetMapping("/random")
    public AffirmationDto getRandom() {
        return AffirmationDto.fromModel(service.getRandom());
    }

    @Operation(summary = "Get affirmations by category")
    @GetMapping("/category/{category}")
    public List<AffirmationDto> getByCategory(@PathVariable String category) {
        return service.getByCategory(category)
                .stream()
                .map(AffirmationDto::fromModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get personalized affirmations")
    @GetMapping("/personalized")
    public List<AffirmationDto> getPersonalized(@RequestParam String username) {
        return service.getPersonalized(username)
                .stream()
                .map(AffirmationDto::fromModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Add an affirmation")
    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AffirmationDto> save(@Valid @RequestBody AffirmationDto affirmationDto,
                                               @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        AffirmationDto saved = AffirmationDto.fromModel(service.save(auth.getName(), affirmationDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Update an affirmation")
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AffirmationDto> update(@PathVariable Long id,
                                                 @Valid @RequestBody AffirmationDto affirmationDto,
                                                 @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        AffirmationDto updated = AffirmationDto.fromModel(service.update(id, auth.getName(), affirmationDto));
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an affirmation")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        service.delete(id, auth);
        return ResponseEntity.noContent().build();
    }
}