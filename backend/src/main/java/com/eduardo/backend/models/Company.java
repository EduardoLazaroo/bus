package com.eduardo.backend.models;

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

    private String name;

    private String cnpj;

    private String address;

    private Boolean active = true;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Journey> journeys;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanyLink> companyLinks;
}
