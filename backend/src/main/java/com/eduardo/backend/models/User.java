package com.eduardo.backend.models;

import com.eduardo.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String phone;
    private String profileImage;
    private String cep;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String numero;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CompanyLink> companyLinks;
}