package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyLinkInviteDTO;
import com.eduardo.backend.dtos.CompanyLinkResponseDTO;

import java.util.List;

public interface CompanyLinkService {
    CompanyLinkResponseDTO invite(CompanyLinkInviteDTO dto);
    CompanyLinkResponseDTO approveLink(Long linkId);
    List<CompanyLinkResponseDTO> getLinksForCompany(Long companyId);
    List<CompanyLinkResponseDTO> getLinksForCurrentUser();
}
