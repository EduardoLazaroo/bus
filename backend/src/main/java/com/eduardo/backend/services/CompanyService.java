package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyCreateDTO dto);
    List<CompanyResponseDTO> getPendingCompanies();
    CompanyResponseDTO approveOrRejectCompany(Long companyId, CompanyApproveDTO dto);
    CompanyResponseDTO getCompany(Long companyId);
    List<CompanyResponseDTO> getCompaniesByOwner();
}
