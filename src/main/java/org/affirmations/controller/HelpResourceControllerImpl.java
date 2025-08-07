package org.affirmations.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.affirmations.dto.HelpResourceDto;
import org.affirmations.service.HelpResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/help")
@RequiredArgsConstructor
public class HelpResourceControllerImpl implements HelpResourceController {
    private final HelpResourceService service;

    @Operation(summary = "Get help resources")
    @GetMapping("/resources")
    @Override
    public List<HelpResourceDto> getAll(@RequestParam(required = false) String type) {
        return service.findByType(type).stream()
                .map(HelpResourceDto::fromModel)
                .collect(Collectors.toList());
    }
}
