package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.CompanyLinkInviteDTO;
import com.eduardo.backend.dtos.CompanyLinkResponseDTO;
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

    private final CompanyLinkRepository companyLinkRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyLinkServiceImpl(CompanyLinkRepository companyLinkRepository,
                                  CompanyRepository companyRepository,
                                  UserRepository userRepository) {
        this.companyLinkRepository = companyLinkRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CompanyLinkResponseDTO invite(CompanyLinkInviteDTO dto) {
        User current = SecurityUtils.getCurrentUserOrThrow(userRepository);

        // somente OWNER da company pode convidar
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));

        if (company.getOwner() == null || !company.getOwner().getId().equals(current.getId())) {
            throw new BadRequestException("Apenas o OWNER da empresa pode convidar usuários");
        }

        User invited = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário a ser convidado não encontrado"));

        // evitar convites duplicados
        if (companyLinkRepository.existsByUserAndCompany(invited, company)) {
            throw new BadRequestException("Usuário já possui vínculo com essa empresa");
        }

        CompanyLink link = CompanyLink.builder()
                .company(company)
                .user(invited)
                .approved(false)
                .roleInCompany(dto.getRoleInCompany())
                .build();

        CompanyLink saved = companyLinkRepository.save(link);
        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public CompanyLinkResponseDTO approveLink(Long linkId) {
        User current = SecurityUtils.getCurrentUserOrThrow(userRepository);
        CompanyLink link = companyLinkRepository.findById(linkId)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado"));

        // Apenas OWNER da company pode aprovar
        Company company = link.getCompany();
        if (company.getOwner() == null || !company.getOwner().getId().equals(current.getId())) {
            throw new BadRequestException("Apenas o OWNER da empresa pode aprovar vínculos");
        }

        link.setApproved(true);
        companyLinkRepository.save(link);
        return mapToDTO(link);
    }

    @Override
    public List<CompanyLinkResponseDTO> getLinksForCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));
        List<CompanyLink> list = companyLinkRepository.findByCompany(company);
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CompanyLinkResponseDTO> getLinksForCurrentUser() {
        User current = SecurityUtils.getCurrentUserOrThrow(userRepository);
        List<CompanyLink> list = companyLinkRepository.findByUser(current);
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CompanyLinkResponseDTO mapToDTO(CompanyLink link) {
        return CompanyLinkResponseDTO.builder()
                .id(link.getId())
                .userId(link.getUser() != null ? link.getUser().getId() : null)
                .userName(link.getUser() != null ? link.getUser().getName() : null)
                .companyId(link.getCompany() != null ? link.getCompany().getId() : null)
                .companyName(link.getCompany() != null ? link.getCompany().getName() : null)
                .approved(link.getApproved())
                .roleInCompany(link.getRoleInCompany())
                .build();
    }
}
