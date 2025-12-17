package com.eduardo.backend.repositories;

import com.eduardo.backend.models.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {
	boolean existsByUserId(Long userId);
	Optional<DriverProfile> findByUserId(Long userId);
}