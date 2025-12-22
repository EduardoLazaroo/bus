package com.eduardo.backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class JourneyCreateDTO {
    private String name;
    private String description;
    private List<Long> driverCompanyLinkIds; // company_link ids for drivers
    private List<Long> clientCompanyLinkIds; // company_link ids for clients
    private List<Long> vehicleIds;
}
