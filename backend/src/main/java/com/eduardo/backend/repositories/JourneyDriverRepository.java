package com.eduardo.backend.repositories;

import com.eduardo.backend.models.JourneyDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyDriverRepository extends JpaRepository<JourneyDriver, Long> {
    List<JourneyDriver> findByJourneyId(Long journeyId);
    List<JourneyDriver> findByCompanyLinkId(Long companyLinkId);
}
