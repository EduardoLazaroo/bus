package com.eduardo.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Boolean approved = false; // status do vínculo
}