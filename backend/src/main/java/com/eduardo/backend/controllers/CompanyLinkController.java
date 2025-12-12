package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.CompanyResponseDTO;
import com.eduardo.backend.services.CompanyLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-links")
public class CompanyLinkController {

    private final CompanyLinkService companyLinkService;

    public CompanyLinkController(CompanyLinkService companyLinkService) {
        this.companyLinkService = companyLinkService;
    }

    /**
     * Retorna todas as empresas APROVADAS que o usuário logado
     * ainda NÃO está vinculado. Serve para o cliente escolher
     * em qual empresa quer solicitar participação.
     */
    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('CLIENT', 'DRIVER', 'OWNER')")
    public ResponseEntity<List<CompanyResponseDTO>> getAvailableCompanies() {
        List<CompanyResponseDTO> result = companyLinkService.getAvailableCompaniesForUser();
        return ResponseEntity.ok(result);
    }
}
