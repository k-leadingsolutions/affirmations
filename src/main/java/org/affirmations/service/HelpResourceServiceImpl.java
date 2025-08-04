package org.affirmations.service;

import org.affirmations.model.HelpResource;
import org.affirmations.repository.HelpResourceRepository;
import org.affirmations.util.HelpResourceGenerator;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class HelpResourceServiceImpl implements HelpResourceService {

    private static final Logger logger = LoggerFactory.getLogger(HelpResourceServiceImpl.class);

    private final HelpResourceRepository helpResourceRepository;

    public HelpResourceServiceImpl(HelpResourceRepository helpResourceRepository) {
        this.helpResourceRepository = helpResourceRepository;
    }

    @Override
    public List<HelpResource> findByType(String type) {
        helpResourceRepository.saveAll(HelpResourceGenerator.generateHelpResources());
        if (type == null) {
            logger.debug("Type is null, fetching all help resources");
            List<HelpResource> resources = helpResourceRepository.findAll();
            logger.info("Fetched all help resources, count: {}", resources.size());
            return resources;
        }
        logger.debug("Fetching help resources by type: {}", type);
        List<HelpResource> resources = helpResourceRepository.findByType(type);
        logger.info("Fetched help resources by type '{}', count: {}", type, resources.size());
        return resources;
    }
}