package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.CompanyLinkResponseDTO;
import com.eduardo.backend.enums.CompanyStatus;
import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.exceptions.ResourceNotFoundException;
import com.eduardo.backend.models.Company;
import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.CompanyLinkRepository;
import com.eduardo.backend.repositories.CompanyRepository;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.CompanyLinkService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyLinkServiceImpl implements CompanyLinkService {

    private final CompanyRepository companyRepository;
    private final CompanyLinkRepository companyLinkRepository;
    private final UserRepository userRepository;

    public CompanyLinkServiceImpl(
            CompanyRepository companyRepository,
            CompanyLinkRepository companyLinkRepository,
            UserRepository userRepository
    ) {
        this.companyRepository = companyRepository;
        this.companyLinkRepository = companyLinkRepository;
        this.userRepository = userRepository;
    }

    // 1. Lista empresas aprovadas para o CLIENT solicitar
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

    // 2. CLIENT solicita vínculo
    @Override
    @Transactional
    public CompanyLinkResponseDTO requestAccess(Long companyId) {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        if (company.getStatus() != CompanyStatus.APPROVED) {
            throw new BadRequestException("Empresa não está aprovada ainda");
        }

        if (companyLinkRepository.existsByUserIdAndCompanyId(user.getId(), companyId)) {
            throw new BadRequestException("Você já solicitou acesso a esta empresa");
        }

        CompanyLink link = CompanyLink.builder()
                .user(user)
                .company(company)
                .status(LinkStatus.PENDING)
                .build();

        companyLinkRepository.save(link);

        return mapToDTO(link);
    }

    // 3. OWNER vê solicitações pendentes
    @Override
    public List<CompanyLinkResponseDTO> getPendingRequestsForOwner() {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);

        return companyLinkRepository
                .findByCompanyOwnerIdAndStatus(owner.getId(), LinkStatus.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 4. OWNER aprova
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

    private CompanyLinkResponseDTO mapToDTO(CompanyLink link) {
        return CompanyLinkResponseDTO.builder()
                .id(link.getId())
                .userId(link.getUser().getId())
                .userName(link.getUser().getName())
                .companyId(link.getCompany().getId())
                .companyName(link.getCompany().getCompanyName())
                .status(link.getStatus())
                .build();
    }
}
