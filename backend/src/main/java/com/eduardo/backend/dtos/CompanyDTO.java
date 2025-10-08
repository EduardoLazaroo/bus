package com.eduardo.backend.dtos;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String cnpj;
    private Boolean active;
}