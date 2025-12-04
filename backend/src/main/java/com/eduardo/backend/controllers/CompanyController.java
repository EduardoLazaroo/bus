package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;
import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // OWNER cria company (fica PENDING)
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CompanyResponseDTO> createCompany(
            @RequestBody CompanyCreateDTO dto
    ) {
        CompanyResponseDTO response = companyService.createCompany(dto);
        return ResponseEntity.ok(response);
    }

    // OWNER atualiza empresa (PUT)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CompanyResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CompanyCreateDTO dto
    ) {
        return ResponseEntity.ok(companyService.updateCompany(id, dto));
    }

    // ADMIN vê companies pendentes
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponseDTO>> getPendingCompanies() {
        return ResponseEntity.ok(companyService.getPendingCompanies());
    }

    // ADMIN aprova/rejeita
    @PutMapping("/{companyId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDTO> approveCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyApproveDTO dto
    ) {
        CompanyResponseDTO response = companyService.approveOrRejectCompany(companyId, dto);
        return ResponseEntity.ok(response);
    }

    // OWNER vê suas companies
    @GetMapping("/mine")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<CompanyResponseDTO>> getCompaniesByOwner() {
        return ResponseEntity.ok(companyService.getCompaniesByOwner());
    }

    // qualquer usuário autenticado busca por ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CompanyResponseDTO> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }
}
