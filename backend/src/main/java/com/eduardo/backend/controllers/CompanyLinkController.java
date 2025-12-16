package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.CompanyLinkResponseDTO;
import com.eduardo.backend.services.CompanyLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-links")
public class CompanyLinkController {

    private final CompanyLinkService companyLinkService;

    public CompanyLinkController(CompanyLinkService companyLinkService) {
        this.companyLinkService = companyLinkService;
    }

    // 1. Lista empresas aprovadas para o CLIENT solicitar
    @GetMapping("/available")
    public ResponseEntity<List<CompanyLinkResponseDTO>> getAvailable() {
        return ResponseEntity.ok(companyLinkService.getAvailableCompanies());
    }

    // 2. CLIENT solicita vínculo
    @PostMapping("/request/{companyId}")
    public ResponseEntity<CompanyLinkResponseDTO> request(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyLinkService.requestAccess(companyId));
    }

    // CLIENT vê seus vínculos
    @GetMapping("/mine")
    public ResponseEntity<List<CompanyLinkResponseDTO>> myLinks() {
        return ResponseEntity.ok(companyLinkService.getMyLinks());
    }


    // 3. OWNER vê solicitações pendentes
    @GetMapping("/pending")
    public ResponseEntity<List<CompanyLinkResponseDTO>> pending() {
        return ResponseEntity.ok(companyLinkService.getPendingRequestsForOwner());
    }

    // 4. OWNER aprova
    @PutMapping("/{linkId}/approve")
    public ResponseEntity<CompanyLinkResponseDTO> approve(@PathVariable Long linkId) {
        return ResponseEntity.ok(companyLinkService.approve(linkId));
    }

    // 5. OWNER GET users by company
    @GetMapping("/company/{companyId}/users")
    public ResponseEntity<List<CompanyLinkResponseDTO>> getUsersLinkedToCompany(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(
                companyLinkService.getUsersLinkedToCompany(companyId)
        );
    }
}
