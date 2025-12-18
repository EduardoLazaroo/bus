package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;
import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por todas as operações
 * relacionadas às empresas de transporte.
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * OWNER cria uma empresa.
     * A empresa nasce com status PENDING e aguarda aprovação do ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CompanyResponseDTO> createCompany(
            @RequestBody CompanyCreateDTO dto
    ) {
        CompanyResponseDTO response = companyService.createCompany(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * OWNER atualiza dados da própria empresa.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CompanyResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CompanyCreateDTO dto
    ) {
        return ResponseEntity.ok(companyService.updateCompany(id, dto));
    }

    /**
     * ADMIN lista empresas que ainda estão pendentes de aprovação.
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponseDTO>> getPendingCompanies() {
        return ResponseEntity.ok(companyService.getPendingCompanies());
    }

    /**
     * ADMIN aprova ou rejeita uma empresa.
     * Se aprovada, o vínculo OWNER ↔ COMPANY é criado automaticamente.
     */
    @PutMapping("/{companyId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDTO> approveCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyApproveDTO dto
    ) {
        CompanyResponseDTO response = companyService.approveOrRejectCompany(companyId, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * OWNER visualiza todas as empresas que ele é dono.
     */
    @GetMapping("/mine")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<CompanyResponseDTO>> getCompaniesByOwner() {
        return ResponseEntity.ok(companyService.getCompaniesByOwner());
    }

    /**
     * Qualquer usuário autenticado pode consultar
     * os dados públicos de uma empresa pelo ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CompanyResponseDTO> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }
}
