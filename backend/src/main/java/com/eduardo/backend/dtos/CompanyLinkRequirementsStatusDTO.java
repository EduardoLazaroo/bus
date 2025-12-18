package com.eduardo.backend.dtos;

import lombok.Builder;

@Builder
public record CompanyLinkRequirementsStatusDTO(
        boolean hasClient,
        boolean hasDriver,
        boolean hasVehicle,
        boolean canCreateJourney
) {}
