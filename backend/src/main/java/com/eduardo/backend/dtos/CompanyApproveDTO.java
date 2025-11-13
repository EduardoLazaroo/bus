package com.eduardo.backend.dtos;

import lombok.Data;

@Data
public class CompanyApproveDTO {
    private Boolean approve;
    private String reason; // opcional
}
