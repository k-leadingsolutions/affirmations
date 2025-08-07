package org.affirmations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.affirmations.model.HelpResource;
import org.affirmations.repository.HelpResourceRepository;
import org.affirmations.util.HelpResourceGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HelpResourceServiceImpl implements HelpResourceService {

    private final HelpResourceRepository helpResourceRepository;

    @Override
    public List<HelpResource> findByType(String type) {
        helpResourceRepository.saveAll(HelpResourceGenerator.generateHelpResources());
        if (type == null) {
            log.debug("Type is null, fetching all help resources");
            List<HelpResource> resources = helpResourceRepository.findAll();
            log.info("Fetched all help resources, count: {}", resources.size());
            return resources;
        }
        log.debug("Fetching help resources by type: {}", type);
        List<HelpResource> resources = helpResourceRepository.findByType(type);
        log.info("Fetched help resources by type '{}', count: {}", type, resources.size());
        return resources;
    }
}