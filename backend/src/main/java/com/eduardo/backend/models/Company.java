package com.eduardo.backend.models;

import com.eduardo.backend.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidade que representa uma empresa de transporte.
 * Centraliza dados operacionais, financeiros e vínculo com usuários.
 */
@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados básicos da empresa
    private String companyName;
    private String cnpj;

    // Endereço
    private String country;
    private String state;
    private String city;
    private String district;
    private String street;
    private String number;
    private String complement;
    private String zipCode;

    // Contato
    private String phone;
    private String mobilePhone;
    private String email;

    // Informações financeiras
    private String paymentType;
    private String paymentInfo;

    private String recipientName; // destinatário
    private String unitType;      // tipo unidade

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyStatus status = CompanyStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanyLink> companyLinks;
}
