package com.eduardo.backend.models;

import com.eduardo.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
// Entidade que representa o usuário no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados básicos
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    // Senha armazenada sempre criptografada
    private String password;

    // Papel do usuário no sistema (ADMIN, OWNER, etc)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Informações de contato e endereço
    private String phone;
    private String profileImage;
    private String cep;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String numero;

    // Vínculos do usuário com empresas
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CompanyLink> companyLinks;
}
