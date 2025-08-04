package org.affirmations.service;

import org.affirmations.model.HelpResource;

import java.util.List;

public interface HelpResourceService {
    List<HelpResource> findByType(String type);
}
