package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyLinkRequestDTO;
import com.eduardo.backend.dtos.CompanyLinkResponseDTO;

import java.util.List;

/**
 * Define as regras de negócio do vínculo
 * entre usuários e empresas.
 */
public interface CompanyLinkService {

    // Lista empresas disponíveis para solicitação de vínculo
    List<CompanyLinkResponseDTO> getAvailableCompanies();

    // Cria uma solicitação de vínculo
    CompanyLinkResponseDTO requestAccess(CompanyLinkRequestDTO dto);

    // CLIENT visualiza seus próprios vínculos
    List<CompanyLinkResponseDTO> getMyLinks();

    // OWNER visualiza solicitações pendentes
    List<CompanyLinkResponseDTO> getPendingRequestsForOwner();

    // OWNER aprova uma solicitação
    CompanyLinkResponseDTO approve(Long linkId);

    // OWNER lista usuários aprovados de uma empresa
    List<CompanyLinkResponseDTO> getUsersLinkedToCompany(Long companyId);
}
