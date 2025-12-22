package com.eduardo.backend.repositories;

import com.eduardo.backend.models.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey, Long> {
    List<Journey> findByCompanyLinkId(Long companyLinkId);
}
