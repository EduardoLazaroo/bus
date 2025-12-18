package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyLinkRequestDTO {
    @NotNull
    private Long companyId;
    
    @NotNull
    private UserRole requestedRole; // CLIENT ou DRIVER
}
