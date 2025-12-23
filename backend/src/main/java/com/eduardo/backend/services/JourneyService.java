package com.eduardo.backend.services;

import com.eduardo.backend.dtos.JourneyCreateDTO;
import com.eduardo.backend.dtos.JourneyOptionsDTO;
import com.eduardo.backend.dtos.JourneyResponseDTO;

import java.util.List;

public interface JourneyService {
    JourneyResponseDTO create(JourneyCreateDTO dto);
    List<JourneyResponseDTO> listMyCompanyJourneys();
    List<JourneyResponseDTO> listMyJourneys();
    JourneyResponseDTO get(Long id);
    JourneyResponseDTO update(Long id, JourneyCreateDTO dto);
    void deactivate(Long id);
    JourneyOptionsDTO getOptions();
}