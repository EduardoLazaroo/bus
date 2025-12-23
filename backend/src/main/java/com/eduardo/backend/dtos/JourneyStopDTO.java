package com.eduardo.backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JourneyStopDTO {
    private Long id;
    private Integer seqOrder;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
