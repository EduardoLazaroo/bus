package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.UserRole;
import lombok.Data;

@Data
public class CompanyLinkInviteDTO {
    private Long userId;
    private Long companyId;
    private UserRole roleInCompany; // DRIVER or CLIENT
}
