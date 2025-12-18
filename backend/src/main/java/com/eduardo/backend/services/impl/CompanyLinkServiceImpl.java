package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.CompanyLinkRequestDTO;
import com.eduardo.backend.dtos.CompanyLinkRequirementsStatusDTO;
import com.eduardo.backend.dtos.CompanyLinkResponseDTO;
import com.eduardo.backend.enums.CompanyStatus;
import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.enums.UserRole;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.exceptions.ResourceNotFoundException;
import com.eduardo.backend.models.Company;
import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.*;
import com.eduardo.backend.services.CompanyLinkService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação das regras de vínculo entre usuários e empresas.
 * Toda validação de permissão e status acontece aqui.
 */
@Service
public class CompanyLinkServiceImpl implements CompanyLinkService {

    private final CompanyRepository companyRepository;
    private final CompanyLinkRepository companyLinkRepository;
    private final UserRepository userRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final VehicleRepository vehicleRepository;

    public CompanyLinkServiceImpl(
            CompanyRepository companyRepository,
            CompanyLinkRepository companyLinkRepository,
            UserRepository userRepository,
            DriverProfileRepository driverProfileRepository,
            VehicleRepository vehicleRepository
    ) {
        this.companyRepository = companyRepository;
        this.companyLinkRepository = companyLinkRepository;
        this.userRepository = userRepository;
        this.driverProfileRepository = driverProfileRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Lista empresas aprovadas que o usuário ainda não solicitou vínculo.
     */
    @Override
    public List<CompanyLinkResponseDTO> getAvailableCompanies() {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        return companyRepository.findAll().stream()
                .filter(c -> c.getStatus() == CompanyStatus.APPROVED)
                .filter(c -> !companyLinkRepository.existsByUserIdAndCompanyId(user.getId(), c.getId()))
                .map(c -> CompanyLinkResponseDTO.builder()
                        .id(null)
                        .userId(user.getId())
                        .userName(user.getName())
                        .companyId(c.getId())
                        .companyName(c.getCompanyName())
                        .status(null)
                        .build()
                ).collect(Collectors.toList());
    }

    /**
     * Cria uma solicitação de vínculo para o CLIENT.
     */
    @Override
    @Transactional
    public CompanyLinkResponseDTO requestAccess(CompanyLinkRequestDTO dto) {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        if (company.getStatus() != CompanyStatus.APPROVED) {
            throw new BadRequestException("Empresa não está aprovada ainda");
        }

        if (companyLinkRepository.existsByUserIdAndCompanyId(user.getId(), company.getId())) {
            throw new BadRequestException("Você já solicitou acesso a esta empresa");
        }

        CompanyLink link = CompanyLink.builder()
                .user(user)
                .company(company)
                .status(LinkStatus.PENDING)
                .roleInCompany(dto.getRequestedRole())
                .build();

        companyLinkRepository.save(link);

        return mapToDTO(link);
    }

    /**
     * CLIENT visualiza todos os seus vínculos.
     */
    @Override
    public List<CompanyLinkResponseDTO> getMyLinks() {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        return companyLinkRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * OWNER visualiza solicitações pendentes das suas empresas.
     */
    @Override
    public List<CompanyLinkResponseDTO> getPendingRequestsForOwner() {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);

        return companyLinkRepository
                .findByCompanyOwnerIdAndStatus(owner.getId(), LinkStatus.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * OWNER aprova uma solicitação de vínculo.
     */
    @Override
    @Transactional
    public CompanyLinkResponseDTO approve(Long linkId) {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);

        CompanyLink link = companyLinkRepository.findById(linkId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada"));

        if (!link.getCompany().getOwner().getId().equals(owner.getId())) {
            throw new BadRequestException("Você não é dono desta empresa");
        }

        link.setStatus(LinkStatus.APPROVED);
        companyLinkRepository.save(link);

        return mapToDTO(link);
    }

    /**
     * OWNER lista usuários aprovados vinculados a uma empresa.
     */
    @Override
    public List<CompanyLinkResponseDTO> getUsersLinkedToCompany(Long companyId) {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        if (!company.getOwner().getId().equals(owner.getId())) {
            throw new BadRequestException("Você não é dono desta empresa");
        }

        return companyLinkRepository
                .findByCompanyIdAndStatus(companyId, LinkStatus.APPROVED)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public CompanyLinkRequirementsStatusDTO checkRequirements() {

        var user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        CompanyLink companyLink = companyLinkRepository
                .findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() ->
                        new BadRequestException("Usuário não possui vínculo aprovado com empresa")
                );

        Long companyLinkId = companyLink.getId();

        Long companyId = companyLink.getCompany().getId();

        var approvedLinks = companyLinkRepository.findByCompanyIdAndStatus(companyId, LinkStatus.APPROVED);

        boolean hasClient = approvedLinks.stream()
                .anyMatch(l -> l.getRoleInCompany() == UserRole.CLIENT);

        boolean hasDriver = approvedLinks.stream()
                .anyMatch(l -> l.getRoleInCompany() == UserRole.DRIVER);

        boolean hasVehicle = vehicleRepository.existsByCompanyLinkIdAndActiveTrue(
                companyLinkId
        );

        return CompanyLinkRequirementsStatusDTO.builder()
                .hasClient(hasClient)
                .hasDriver(hasDriver)
                .hasVehicle(hasVehicle)
                .canCreateJourney(hasClient && hasDriver && hasVehicle)
                .build();
    }

    /**
     * Converte entidade em DTO de resposta.
     */
    private CompanyLinkResponseDTO mapToDTO(CompanyLink link) {
        return CompanyLinkResponseDTO.builder()
                .id(link.getId())
                .userId(link.getUser().getId())
                .userName(link.getUser().getName())
                .companyId(link.getCompany().getId())
                .companyName(link.getCompany().getCompanyName())
                .role(link.getRoleInCompany())
                .status(link.getStatus())
                .build();
    }
}
