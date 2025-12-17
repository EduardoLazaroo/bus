package com.eduardo.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    private Integer capacity;

    private String type;

    private Integer year;

    private String color;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // Vínculo operacional (quem cadastrou / a qual empresa pertence)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_link_id", nullable = false)
    private CompanyLink companyLink;
}
