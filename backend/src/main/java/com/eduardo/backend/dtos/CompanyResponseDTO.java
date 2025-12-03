package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.CompanyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponseDTO {
    private Long id;
    private String companyName;
    private String cnpj;

    private String country;
    private String state;
    private String city;
    private String district;
    private String street;
    private String number;
    private String complement;
    private String zipCode;

    private String phone;
    private String mobilePhone;
    private String email;

    private String paymentType;
    private String paymentInfo;

    private String recipientName;
    private String unitType;

    private CompanyStatus status;
    private Long ownerId;
    private String ownerName;
}
