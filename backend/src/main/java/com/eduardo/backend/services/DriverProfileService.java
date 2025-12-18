package com.eduardo.backend.services;

import com.eduardo.backend.dtos.DriverProfileCreateDTO;
import com.eduardo.backend.dtos.DriverProfileResponseDTO;

// Contrato da camada de serviço do perfil de motorista
public interface DriverProfileService {

	// Criação ou atualização do perfil do usuário logado
	DriverProfileResponseDTO createOrUpdate(DriverProfileCreateDTO dto);

	// Consulta do próprio perfil
	DriverProfileResponseDTO getMyProfile();
}