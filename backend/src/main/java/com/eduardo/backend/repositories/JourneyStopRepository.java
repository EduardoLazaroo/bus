package com.eduardo.backend.repositories;

import com.eduardo.backend.models.JourneyStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyStopRepository extends JpaRepository<JourneyStop, Long> {
    List<JourneyStop> findByJourneyIdOrderBySeqOrder(Long journeyId);
}
