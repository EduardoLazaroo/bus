package com.eduardo.backend.services;

import com.eduardo.backend.dtos.CompanyResponseDTO;
import java.util.List;

public interface CompanyLinkService {
    List<CompanyResponseDTO> getAvailableCompaniesForUser();
}
