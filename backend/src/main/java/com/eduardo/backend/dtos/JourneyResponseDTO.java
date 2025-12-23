package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.JourneyStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JourneyResponseDTO {
    private Long id;
    private String name;
    private String description;
    private JourneyStatus status;
    private Long companyLinkId;
    private List<Long> driverCompanyLinkIds;
    private List<Long> clientCompanyLinkIds;
    private List<Long> vehicleIds;
    private List<JourneyStopDTO> stops;
}
