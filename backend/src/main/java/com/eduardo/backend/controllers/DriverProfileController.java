package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.DriverProfileCreateDTO;
import com.eduardo.backend.dtos.DriverProfileResponseDTO;
import com.eduardo.backend.services.DriverProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/driver-profile", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DriverProfileController {

    private final DriverProfileService driverProfileService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverProfileResponseDTO> createOrUpdate(@RequestBody @Valid DriverProfileCreateDTO dto) {
        DriverProfileResponseDTO response = driverProfileService.createOrUpdate(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mine")
    public ResponseEntity<DriverProfileResponseDTO> getMyProfile() {
        DriverProfileResponseDTO response = driverProfileService.getMyProfile();
        return ResponseEntity.ok(response);
    }
}
