package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.VehicleCreateDTO;
import com.eduardo.backend.dtos.VehicleResponseDTO;
import com.eduardo.backend.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Cria um novo veículo vinculado à empresa do usuário logado
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(
            @RequestBody VehicleCreateDTO dto
    ) {
        return ResponseEntity.ok(vehicleService.create(dto));
    }

    // Lista todos os veículos do usuário (ativos e desativados)
    @GetMapping("/mine")
    public ResponseEntity<List<VehicleResponseDTO>> getMyVehicles() {
        return ResponseEntity.ok(vehicleService.getMyVehicles());
    }

    // Atualiza os dados de um veículo pertencente ao usuário
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(
            @PathVariable Long id,
            @RequestBody VehicleCreateDTO dto
    ) {
        return ResponseEntity.ok(vehicleService.update(id, dto));
    }

    // Desativa (soft delete) um veículo do usuário
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        vehicleService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
