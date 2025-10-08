package com.eduardo.backend.dtos;

import lombok.Data;

@Data
public class VehicleDTO {
    private Long id;
    private String plate;
    private String model;
    private Long companyId;
}