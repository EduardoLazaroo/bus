package com.eduardo.backend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCreateDTO {
    private String model;
    private String licensePlate;
    private Integer capacity;
    private String type;
    private Integer year;
    private String color;
}
