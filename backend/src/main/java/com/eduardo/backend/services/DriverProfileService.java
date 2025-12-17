package com.eduardo.backend.services;

import com.eduardo.backend.dtos.DriverProfileCreateDTO;
import com.eduardo.backend.dtos.DriverProfileResponseDTO;

public interface DriverProfileService {
	DriverProfileResponseDTO createOrUpdate(DriverProfileCreateDTO dto);
	DriverProfileResponseDTO getMyProfile();
}