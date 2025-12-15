package com.eduardo.backend.models;

import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

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

    // USUÁRIO VINCULADO
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // EMPRESA VINCULADA
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // STATUS DO VÍNCULO (PENDING / APPROVED / REJECTED)
    @Enumerated(EnumType.STRING)
    private LinkStatus status;

    // ROLE DO USUÁRIO DENTRO DA EMPRESA (OWNER / ADMIN / EMPLOYEE / VIEWER)
    @Enumerated(EnumType.STRING)
    private UserRole roleInCompany;
}
