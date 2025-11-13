package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyLinkResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long companyId;
    private String companyName;
    private Boolean approved;
    private UserRole roleInCompany;
}
