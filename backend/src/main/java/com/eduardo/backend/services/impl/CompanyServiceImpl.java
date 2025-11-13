package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;
import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.enums.CompanyStatus;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.exceptions.ResourceNotFoundException;
import com.eduardo.backend.models.Company;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.CompanyRepository;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.CompanyService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CompanyResponseDTO createCompany(CompanyCreateDTO dto) {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);

        // somente OWNER pode criar empresa
        if (owner.getRole() == null || !owner.getRole().name().equals("OWNER")) {
            throw new BadRequestException("Apenas usuários com role OWNER podem criar empresas");
        }

        Company company = Company.builder()
                .name(dto.getName())
                .cnpj(dto.getCnpj())
                .address(dto.getAddress())
                .status(CompanyStatus.PENDING)
                .active(false)
                .owner(owner)
                .build();

        Company saved = companyRepository.save(company);
        return mapToDTO(saved);
    }

    @Override
    public List<CompanyResponseDTO> getPendingCompanies() {
        List<Company> list = companyRepository.findByStatus(CompanyStatus.PENDING);
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CompanyResponseDTO approveOrRejectCompany(Long companyId, CompanyApproveDTO dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));

        if (dto.getApprove() != null && dto.getApprove()) {
            company.setStatus(CompanyStatus.APPROVED);
            company.setActive(true);
        } else {
            company.setStatus(CompanyStatus.REJECTED);
            company.setActive(false);
        }

        companyRepository.save(company);
        return mapToDTO(company);
    }

    @Override
    public CompanyResponseDTO getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));
        return mapToDTO(company);
    }

    @Override
    public List<CompanyResponseDTO> getCompaniesByOwner() {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);
        List<Company> companies = companyRepository.findAll()
                .stream()
                .filter(c -> c.getOwner() != null && c.getOwner().getId().equals(owner.getId()))
                .collect(Collectors.toList());
        return companies.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CompanyResponseDTO mapToDTO(Company c) {
        Long ownerId = c.getOwner() != null ? c.getOwner().getId() : null;
        String ownerName = c.getOwner() != null ? c.getOwner().getName() : null;
        return CompanyResponseDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .cnpj(c.getCnpj())
                .address(c.getAddress())
                .active(c.getActive())
                .status(c.getStatus())
                .ownerId(ownerId)
                .ownerName(ownerName)
                .build();
    }
}
