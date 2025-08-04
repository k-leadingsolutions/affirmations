package org.affirmations.repository;

import org.affirmations.model.User;
import org.affirmations.model.Intention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntentionRepository extends JpaRepository<Intention, Long> {
    List<Intention> findByUser(User user);
}