package com.eduardo.backend.models;

import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa o vínculo entre um usuário e uma empresa.
 * Essa entidade controla status e papel do usuário dentro da empresa.
 */
@Entity
@Table(name = "company_links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuário que está solicitando ou possui vínculo
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Empresa à qual o usuário está vinculado
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // Status do vínculo (PENDING, APPROVED, REJECTED)
    @Enumerated(EnumType.STRING)
    private LinkStatus status;

    // Papel do usuário dentro da empresa após aprovação
    @Enumerated(EnumType.STRING)
    private UserRole roleInCompany;
}
