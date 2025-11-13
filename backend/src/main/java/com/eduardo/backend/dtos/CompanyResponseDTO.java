package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.CompanyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponseDTO {
    private Long id;
    private String name;
    private String cnpj;
    private String address;
    private Boolean active;
    private CompanyStatus status;
    private Long ownerId;
    private String ownerName;
}
