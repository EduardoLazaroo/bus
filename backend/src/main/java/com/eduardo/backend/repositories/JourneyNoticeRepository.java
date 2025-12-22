package com.eduardo.backend.repositories;

import com.eduardo.backend.models.JourneyNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyNoticeRepository extends JpaRepository<JourneyNotice, Long> {
    List<JourneyNotice> findByJourneyIdOrderByCreatedAtDesc(Long journeyId);
}
