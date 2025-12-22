package com.eduardo.backend.repositories;

import com.eduardo.backend.models.JourneyPoll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyPollRepository extends JpaRepository<JourneyPoll, Long> {
    List<JourneyPoll> findByJourneyIdOrderByCreatedAtDesc(Long journeyId);
}
