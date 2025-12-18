package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.CompanyLinkRequestDTO;
import com.eduardo.backend.dtos.CompanyLinkResponseDTO;
import com.eduardo.backend.services.CompanyLinkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelo fluxo de vínculo entre usuário e empresa.
 * Aqui passam todas as ações de solicitação, aprovação e listagem de vínculos.
 */
@RestController
@RequestMapping("/api/company-links")
public class CompanyLinkController {

    private final CompanyLinkService companyLinkService;

    public CompanyLinkController(CompanyLinkService companyLinkService) {
        this.companyLinkService = companyLinkService;
    }

    /**
     * Lista empresas aprovadas que o CLIENT pode solicitar vínculo.
     * Exclui empresas onde o usuário já possui solicitação ou vínculo.
     */
    @GetMapping("/available")
    public ResponseEntity<List<CompanyLinkResponseDTO>> getAvailable() {
        return ResponseEntity.ok(companyLinkService.getAvailableCompanies());
    }

    /**
     * CLIENT solicita vínculo com uma empresa,
     * informando o papel desejado dentro dela.
     */
    @PostMapping("/request")
    public ResponseEntity<CompanyLinkResponseDTO> requestWithRole(
            @RequestBody @Valid CompanyLinkRequestDTO dto
    ) {
        return ResponseEntity.ok(companyLinkService.requestAccess(dto));
    }

    /**
     * CLIENT visualiza todos os seus vínculos
     * (pendentes, aprovados ou rejeitados).
     */
    @GetMapping("/mine")
    public ResponseEntity<List<CompanyLinkResponseDTO>> myLinks() {
        return ResponseEntity.ok(companyLinkService.getMyLinks());
    }

    /**
     * OWNER visualiza todas as solicitações pendentes
     * das empresas que ele é dono.
     */
    @GetMapping("/pending")
    public ResponseEntity<List<CompanyLinkResponseDTO>> pending() {
        return ResponseEntity.ok(companyLinkService.getPendingRequestsForOwner());
    }

    /**
     * OWNER aprova uma solicitação de vínculo específica.
     */
    @PutMapping("/{linkId}/approve")
    public ResponseEntity<CompanyLinkResponseDTO> approve(@PathVariable Long linkId) {
        return ResponseEntity.ok(companyLinkService.approve(linkId));
    }

    /**
     * OWNER lista todos os usuários aprovados
     * vinculados a uma empresa específica.
     */
    @GetMapping("/company/{companyId}/users")
    public ResponseEntity<List<CompanyLinkResponseDTO>> getUsersLinkedToCompany(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(
                companyLinkService.getUsersLinkedToCompany(companyId)
        );
    }
}
