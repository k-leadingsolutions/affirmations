package org.affirmations.repository;

import org.affirmations.model.Affirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffirmationRepository extends JpaRepository<Affirmation, Long> {
    List<Affirmation> findByCategory(String category);
    List<Affirmation> findBySubmittedBy(String submittedBy);
}
