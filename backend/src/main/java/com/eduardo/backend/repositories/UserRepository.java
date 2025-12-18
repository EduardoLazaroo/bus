package com.eduardo.backend.repositories;

import com.eduardo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Camada responsável apenas por acesso a dados do usuário
public interface UserRepository extends JpaRepository<User, Long> {

    // Busca usuário pelo email (base para login e segurança)
    Optional<User> findByEmail(String email);
}
