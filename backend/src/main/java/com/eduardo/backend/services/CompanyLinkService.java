package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyLinkResponseDTO;

import java.util.List;

public interface CompanyLinkService {
    List<CompanyLinkResponseDTO> getAvailableCompanies();
    CompanyLinkResponseDTO requestAccess(Long companyId);
    List<CompanyLinkResponseDTO> getMyLinks();
    List<CompanyLinkResponseDTO> getPendingRequestsForOwner();
    CompanyLinkResponseDTO approve(Long linkId);
    List<CompanyLinkResponseDTO> getUsersLinkedToCompany(Long companyId);
}
