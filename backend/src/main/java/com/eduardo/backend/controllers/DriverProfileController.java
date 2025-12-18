package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.DriverProfileCreateDTO;
import com.eduardo.backend.dtos.DriverProfileResponseDTO;
import com.eduardo.backend.services.DriverProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "/api/driver-profile",
        produces = MediaType.APPLICATION_JSON_VALUE
)
// Controller responsável apenas por expor endpoints do perfil de motorista
@RequiredArgsConstructor
public class DriverProfileController {

    private final DriverProfileService driverProfileService;

    // Cria ou atualiza o perfil do motorista do usuário logado
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverProfileResponseDTO> createOrUpdate(
            @RequestBody @Valid DriverProfileCreateDTO dto
    ) {
        DriverProfileResponseDTO response = driverProfileService.createOrUpdate(dto);
        return ResponseEntity.ok(response);
    }

    // Retorna o perfil de motorista do usuário autenticado
    @GetMapping("/mine")
    public ResponseEntity<DriverProfileResponseDTO> getMyProfile() {
        DriverProfileResponseDTO response = driverProfileService.getMyProfile();
        return ResponseEntity.ok(response);
    }
}
