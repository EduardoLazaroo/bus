package com.eduardo.backend.repositories;

import com.eduardo.backend.models.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositório responsável pelo acesso aos dados de DriverProfile
public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {

	// Verifica se o usuário já possui perfil de motorista
	boolean existsByUserId(Long userId);

	// Busca perfil pelo ID do usuário (base de todo o fluxo)
	Optional<DriverProfile> findByUserId(Long userId);
}