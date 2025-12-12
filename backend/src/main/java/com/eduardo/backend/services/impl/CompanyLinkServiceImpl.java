package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.CompanyResponseDTO;
import com.eduardo.backend.enums.CompanyStatus;
import com.eduardo.backend.models.Company;
import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.CompanyLinkRepository;
import com.eduardo.backend.repositories.CompanyRepository;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.CompanyLinkService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyLinkServiceImpl implements CompanyLinkService {

    private final CompanyLinkRepository linkRepo;
    private final CompanyRepository companyRepo;
    private final UserRepository userRepo;

    public CompanyLinkServiceImpl(
            CompanyLinkRepository linkRepo,
            CompanyRepository companyRepo,
            UserRepository userRepo
    ) {
        this.linkRepo = linkRepo;
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
    }

    /**
     * Retorna as empresas APROVADAS para as quais o usuário logado AINDA NÃO
     * possui vínculo. Serve como lista de "empresas disponíveis" para solicitar acesso.
     */
    @Override
    public List<CompanyResponseDTO> getAvailableCompaniesForUser() {

        // Obtém usuário logado
        User user = SecurityUtils.getCurrentUserOrThrow(userRepo);

        // Pega todas as empresas aprovadas
        List<Company> approvedCompanies = companyRepo.findByStatus(CompanyStatus.APPROVED);

        // Lista de IDs de empresas que já possuem vínculo com o usuário
        List<Long> companiesLinked = linkRepo.findByUserId(user.getId()).stream()
                .map(link -> link.getCompany().getId())
                .collect(Collectors.toList());

        // Filtra apenas as empresas nas quais o usuário ainda pode solicitar vínculo
        List<Company> availableCompanies = approvedCompanies.stream()
                .filter(company -> !companiesLinked.contains(company.getId()))
                .collect(Collectors.toList());

        return availableCompanies.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte Company -> CompanyResponseDTO seguindo o mesmo padrão
     * utilizado no CompanyService, garantindo consistência no retorno.
     */
    private CompanyResponseDTO mapToDTO(Company c) {
        Long ownerId = c.getOwner() != null ? c.getOwner().getId() : null;
        String ownerName = c.getOwner() != null ? c.getOwner().getName() : null;

        return CompanyResponseDTO.builder()
                .id(c.getId())
                .companyName(c.getCompanyName())
                .cnpj(c.getCnpj())
                .country(c.getCountry())
                .state(c.getState())
                .city(c.getCity())
                .district(c.getDistrict())
                .street(c.getStreet())
                .phone(c.getPhone())
                .zipCode(c.getZipCode())
                .number(c.getNumber())
                .complement(c.getComplement())
                .email(c.getEmail())
                .paymentType(c.getPaymentType())
                .paymentInfo(c.getPaymentInfo())
                .recipientName(c.getRecipientName())
                .mobilePhone(c.getMobilePhone())
                .unitType(c.getUnitType())
                .status(c.getStatus())
                .ownerId(ownerId)
                .ownerName(ownerName)
                .build();
    }
}
