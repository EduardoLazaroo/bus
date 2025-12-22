package com.eduardo.backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JourneyOptionsDTO {
    private List<CompanyLinkResponseDTO> drivers;
    private List<CompanyLinkResponseDTO> clients;
    private List<VehicleResponseDTO> vehicles;
}
