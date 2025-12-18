package com.eduardo.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
// Entidade que representa um veículo da empresa
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados básicos do veículo
    @Column(nullable = false)
    private String model;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    private Integer capacity;
    private String type;
    private Integer year;
    private String color;

    // Soft delete: veículo permanece no banco, mas fica inativo
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // Vínculo com a empresa via CompanyLink
    // Define a quem o veículo pertence operacionalmente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_link_id", nullable = false)
    private CompanyLink companyLink;
}
