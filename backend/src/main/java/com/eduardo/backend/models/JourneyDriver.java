package com.eduardo.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "journey_drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id", nullable = false)
    private Journey journey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_link_id", nullable = false)
    private CompanyLink companyLink; // driver is a CompanyLink with role DRIVER
}
