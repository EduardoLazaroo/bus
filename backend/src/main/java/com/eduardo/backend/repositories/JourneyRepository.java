package com.eduardo.backend.repositories;

import com.eduardo.backend.models.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyRepository extends JpaRepository<Journey, Long> {
}