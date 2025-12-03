package com.eduardo.backend.models;

import com.eduardo.backend.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String cnpj;

    private String country;
    private String state;
    private String city;
    private String district;      // bairro
    private String street;
    private String number;
    private String complement;
    private String zipCode;

    private String phone;
    private String mobilePhone;
    private String email;

    private String paymentType;
    private String paymentInfo;

    private String recipientName; // destinatário
    private String unitType;      // tipo unidade


    @Enumerated(EnumType.STRING)
    private CompanyStatus status = CompanyStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Journey> journeys;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanyLink> companyLinks;
}
