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

        if (owner.getRole() == null || !owner.getRole().name().equals("OWNER")) {
            throw new BadRequestException("Apenas usuários com role OWNER podem criar empresas");
        }

        Company company = Company.builder()
                .companyName(dto.getCompanyName())
                .cnpj(dto.getCnpj())
                .country(dto.getCountry())
                .state(dto.getState())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .street(dto.getStreet())
                .phone(dto.getPhone())
                .zipCode(dto.getZipCode())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .email(dto.getEmail())
                .paymentType(dto.getPaymentType())
                .paymentInfo(dto.getPaymentInfo())
                .recipientName(dto.getRecipientName())
                .mobilePhone(dto.getMobilePhone())
                .unitType(dto.getUnitType())

                // padrões do sistema
                .status(CompanyStatus.PENDING)
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
        } else {
            company.setStatus(CompanyStatus.REJECTED);
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
