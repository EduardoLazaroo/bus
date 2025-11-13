package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.CompanyLinkInviteDTO;
import com.eduardo.backend.dtos.CompanyLinkResponseDTO;
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

    // Owner convida usuário (cria vínculo pendente)
    @PostMapping("/invite")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CompanyLinkResponseDTO> invite(@RequestBody CompanyLinkInviteDTO dto) {
        return ResponseEntity.ok(companyLinkService.invite(dto));
    }

    // Owner aprova vínculo
    @PutMapping("/{linkId}/approve")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CompanyLinkResponseDTO> approve(@PathVariable Long linkId) {
        return ResponseEntity.ok(companyLinkService.approveLink(linkId));
    }

    // Listar vínculos de uma company (owner ou admin/global)
    @GetMapping("/company/{companyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CompanyLinkResponseDTO>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyLinkService.getLinksForCompany(companyId));
    }

    // Vínculos do usuário atual
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CompanyLinkResponseDTO>> getMine() {
        return ResponseEntity.ok(companyLinkService.getLinksForCurrentUser());
    }
}
