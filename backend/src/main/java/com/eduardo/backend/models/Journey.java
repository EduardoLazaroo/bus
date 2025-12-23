package com.eduardo.backend.models;

import com.eduardo.backend.enums.JourneyStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "journeys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyStatus status = JourneyStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_link_id")
    private CompanyLink companyLink;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    private List<JourneyDriver> drivers;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    private List<JourneyClient> clients;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    private List<JourneyVehicle> vehicles;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JourneyStop> stops;
}

