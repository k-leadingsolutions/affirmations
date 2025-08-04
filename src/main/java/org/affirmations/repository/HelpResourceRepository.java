package org.affirmations.repository;

import org.affirmations.model.HelpResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpResourceRepository extends JpaRepository<HelpResource, Long> {
    List<HelpResource> findByType(String type);
}