package com.eduardo.backend.repositories;

import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.enums.LinkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável por consultas relacionadas
 * aos vínculos entre usuários e empresas.
 */
public interface CompanyLinkRepository extends JpaRepository<CompanyLink, Long> {

    // Verifica se o usuário já possui vínculo ou solicitação com a empresa
    boolean existsByUserIdAndCompanyId(Long userId, Long companyId);

    // Lista solicitações pendentes para empresas do OWNER
    List<CompanyLink> findByCompanyOwnerIdAndStatus(Long ownerId, LinkStatus status);

    // CLIENT visualiza todos os seus vínculos
    List<CompanyLink> findByUserId(Long userId);

    // Busca vínculo específico do usuário por status
    Optional<CompanyLink> findByUserIdAndStatus(Long userId, LinkStatus status);

    // Lista usuários aprovados de uma empresa
    List<CompanyLink> findByCompanyIdAndStatus(Long companyId, LinkStatus status);
}
