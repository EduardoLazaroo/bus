package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.JourneyCreateDTO;
import com.eduardo.backend.dtos.JourneyOptionsDTO;
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

    // Cria uma nova jornada
    @PostMapping
    public ResponseEntity<JourneyResponseDTO> create(@RequestBody JourneyCreateDTO dto) {
        return ResponseEntity.ok(journeyService.create(dto));
    }

    // Lista todas as jornadas da empresa do usuário logado
    @GetMapping
    public ResponseEntity<List<JourneyResponseDTO>> list() {
        return ResponseEntity.ok(journeyService.listMyCompanyJourneys());
    }

    // Lista apenas as jornadas vinculadas ao usuário logado
    @GetMapping("/mine")
    public ResponseEntity<List<JourneyResponseDTO>> myList() {
        return ResponseEntity.ok(journeyService.listMyJourneys());
    }

    // Busca uma jornada específica pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<JourneyResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(journeyService.get(id));
    }

    // Atualiza os dados de uma jornada existente
    @PutMapping("/{id}")
    public ResponseEntity<JourneyResponseDTO> update(@PathVariable Long id, @RequestBody JourneyCreateDTO dto) {
        return ResponseEntity.ok(journeyService.update(id, dto));
    }

    // Desativa (remove logicamente) uma jornada
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        journeyService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    // Retorna opções auxiliares para criação/edição de jornadas
    @GetMapping("/options")
    public ResponseEntity<JourneyOptionsDTO> options() {
        return ResponseEntity.ok(journeyService.getOptions());
    }
}