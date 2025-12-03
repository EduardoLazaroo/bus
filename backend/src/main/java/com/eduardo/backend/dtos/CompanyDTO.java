package com.eduardo.backend.dtos;

import lombok.Data;

@Data
public class CompanyDTO {
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
}
