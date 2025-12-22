package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.JourneyCreateDTO;
import com.eduardo.backend.dtos.JourneyResponseDTO;
import com.eduardo.backend.services.JourneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @PostMapping
    public ResponseEntity<JourneyResponseDTO> create(@RequestBody JourneyCreateDTO dto) {
        return ResponseEntity.ok(journeyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<JourneyResponseDTO>> list() {
        return ResponseEntity.ok(journeyService.listMyCompanyJourneys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JourneyResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(journeyService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JourneyResponseDTO> update(@PathVariable Long id, @RequestBody JourneyCreateDTO dto) {
        return ResponseEntity.ok(journeyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        journeyService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/options")
    public ResponseEntity<com.eduardo.backend.dtos.JourneyOptionsDTO> options() {
        return ResponseEntity.ok(journeyService.getOptions());
    }
}