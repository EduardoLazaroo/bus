package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;

import java.util.List;

/**
 * Contrato das regras de negócio
 * relacionadas às empresas.
 */
public interface CompanyService {

    // Cria uma nova empresa
    CompanyResponseDTO createCompany(CompanyCreateDTO dto);

    // Atualiza dados da empresa
    CompanyResponseDTO updateCompany(Long companyId, CompanyCreateDTO dto);

    // Lista empresas pendentes (ADMIN)
    List<CompanyResponseDTO> getPendingCompanies();

    // Aprova ou rejeita empresa
    CompanyResponseDTO approveOrRejectCompany(Long companyId, CompanyApproveDTO dto);

    // Lista empresas do OWNER
    List<CompanyResponseDTO> getCompaniesByOwner();

    // Busca empresa por ID
    CompanyResponseDTO getCompany(Long companyId);
}
