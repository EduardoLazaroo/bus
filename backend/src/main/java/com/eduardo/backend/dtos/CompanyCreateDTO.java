package com.eduardo.backend.dtos;

import lombok.Data;

@Data
public class CompanyCreateDTO {
    private String companyName;
    private String cnpj;
    private String country;
    private String state;
    private String city;
    private String district;      // bairro
    private String street;
    private String phone;
    private String zipCode;       // CEP
    private String number;
    private String complement;
    private String email;
    private String paymentType;
    private String paymentInfo;
    private String recipientName; // destinatário
    private String mobilePhone;   // celular/whatsapp
    private String unitType;      // tipo unidade
}
