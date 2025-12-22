package com.eduardo.backend.repositories;

import com.eduardo.backend.models.JourneyClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyClientRepository extends JpaRepository<JourneyClient, Long> {
    List<JourneyClient> findByJourneyId(Long journeyId);
}
