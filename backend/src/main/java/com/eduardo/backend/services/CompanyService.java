package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyCreateDTO dto);
    CompanyResponseDTO updateCompany(Long companyId, CompanyCreateDTO dto);
    List<CompanyResponseDTO> getPendingCompanies();
    CompanyResponseDTO approveOrRejectCompany(Long companyId, CompanyApproveDTO dto);
    List<CompanyResponseDTO> getCompaniesByOwner();
    CompanyResponseDTO getCompany(Long companyId);
}
