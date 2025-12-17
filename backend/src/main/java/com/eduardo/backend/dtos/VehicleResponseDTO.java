package com.eduardo.backend.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleResponseDTO {
    private Long id;
    private String model;
    private String licensePlate;
    private Integer capacity;
    private String type;
    private Integer year;
    private String color;
    private Boolean active;

    private Long companyLinkId;
}
