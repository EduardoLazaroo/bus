package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.JourneyStatus;
import lombok.Data;

@Data
public class JourneyDTO {
    private Long id;
    private String name;
    private String description;
    private JourneyStatus status;
    private Long companyId;
    private Long driverId;
    private Long vehicleId;
}