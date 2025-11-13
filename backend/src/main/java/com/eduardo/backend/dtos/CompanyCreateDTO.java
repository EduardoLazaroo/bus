package com.eduardo.backend.dtos;

import lombok.Data;

@Data
public class CompanyCreateDTO {
    private String name;
    private String cnpj;
    private String address;
}
