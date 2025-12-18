package com.eduardo.backend.services;

import com.eduardo.backend.dtos.VehicleCreateDTO;
import com.eduardo.backend.dtos.VehicleResponseDTO;

import java.util.List;

public interface VehicleService {

    // Criação de veículo
    VehicleResponseDTO create(VehicleCreateDTO dto);

    // Listagem dos veículos do usuário
    List<VehicleResponseDTO> getMyVehicles();

    // Atualização de veículo
    VehicleResponseDTO update(Long id, VehicleCreateDTO dto);

    // Desativação lógica
    void deactivate(Long id);
}