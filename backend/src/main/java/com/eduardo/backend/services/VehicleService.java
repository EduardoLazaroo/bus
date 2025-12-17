package com.eduardo.backend.services;

import com.eduardo.backend.dtos.VehicleCreateDTO;
import com.eduardo.backend.dtos.VehicleResponseDTO;

import java.util.List;

public interface VehicleService {

    VehicleResponseDTO create(VehicleCreateDTO dto);

    List<VehicleResponseDTO> getMyVehicles();

    VehicleResponseDTO update(Long id, VehicleCreateDTO dto);

    void deactivate(Long id);
}