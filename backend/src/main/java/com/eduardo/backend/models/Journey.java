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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver; // usuário com role MOTORISTA

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToMany
    @JoinTable(
        name = "journey_clients",
        joinColumns = @JoinColumn(name = "journey_id"),
        inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<User> clients;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyStatus status = JourneyStatus.PENDING;
}
