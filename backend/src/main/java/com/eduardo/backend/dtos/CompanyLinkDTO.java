package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.enums.UserRole;

import lombok.Data;

@Data
public class CompanyLinkDTO {
    private Long id;
    private LinkStatus status;
    private UserRole role;
    private Boolean active;
    private Long userId;
    private Long companyId;
}