package com.eduardo.backend.repositories;

import com.eduardo.backend.models.JourneyVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyVehicleRepository extends JpaRepository<JourneyVehicle, Long> {
    List<JourneyVehicle> findByJourneyId(Long journeyId);
}
