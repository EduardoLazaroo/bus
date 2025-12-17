package com.eduardo.backend.repositories;

import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.enums.LinkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyLinkRepository extends JpaRepository<CompanyLink, Long> {

    // Para listar empresas disponíveis
    boolean existsByUserIdAndCompanyId(Long userId, Long companyId);

    // Solicitações para OWNER
    List<CompanyLink> findByCompanyOwnerIdAndStatus(Long ownerId, LinkStatus status);

    //    CLIENT vê seus vínculos
    List<CompanyLink> findByUserId(Long userId);


    Optional<CompanyLink> findByUserIdAndStatus(Long userId, LinkStatus status);


    List<CompanyLink> findByCompanyIdAndStatus(Long companyId, LinkStatus status);
}
