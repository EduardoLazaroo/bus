package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.CompanyCreateDTO;
import com.eduardo.backend.dtos.CompanyResponseDTO;
import com.eduardo.backend.dtos.CompanyApproveDTO;
import com.eduardo.backend.enums.CompanyStatus;
import com.eduardo.backend.enums.UserRole;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.exceptions.ResourceNotFoundException;
import com.eduardo.backend.models.Company;
import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.CompanyRepository;
import com.eduardo.backend.repositories.CompanyLinkRepository;
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
    private final CompanyLinkRepository companyLinkRepository;

    // Injeta todos os repos necessários
    public CompanyServiceImpl(
            CompanyRepository companyRepository,
            UserRepository userRepository,
            CompanyLinkRepository companyLinkRepository
    ) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.companyLinkRepository = companyLinkRepository;
    }

    // Cria a empresa garantindo que o usuário logado é um OWNER.
    @Override
    @Transactional
    public CompanyResponseDTO createCompany(CompanyCreateDTO dto) {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);

        if (owner.getRole() == null || !owner.getRole().equals(UserRole.OWNER)) {
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
                .status(CompanyStatus.PENDING)
                .owner(owner)
                .build();

        Company saved = companyRepository.save(company);
        return mapToDTO(saved);
    }

    // Atualiza a empresa garantindo que quem está editando é o OWNER.
    @Override
    @Transactional
    public CompanyResponseDTO updateCompany(Long companyId, CompanyCreateDTO dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));

        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);
        if (!company.getOwner().getId().equals(owner.getId())) {
            throw new BadRequestException("Usuário não autorizado a atualizar esta empresa");
        }

        company.setCompanyName(dto.getCompanyName());
        company.setCnpj(dto.getCnpj());
        company.setCountry(dto.getCountry());
        company.setState(dto.getState());
        company.setCity(dto.getCity());
        company.setDistrict(dto.getDistrict());
        company.setStreet(dto.getStreet());
        company.setPhone(dto.getPhone());
        company.setZipCode(dto.getZipCode());
        company.setNumber(dto.getNumber());
        company.setComplement(dto.getComplement());
        company.setEmail(dto.getEmail());
        company.setPaymentType(dto.getPaymentType());
        company.setPaymentInfo(dto.getPaymentInfo());
        company.setRecipientName(dto.getRecipientName());
        company.setMobilePhone(dto.getMobilePhone());
        company.setUnitType(dto.getUnitType());

        companyRepository.save(company);
        return mapToDTO(company);
    }

    @Override
    public List<CompanyResponseDTO> getPendingCompanies() {
        return companyRepository.findByStatus(CompanyStatus.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Aprova ou rejeita empresa E cria automaticamente o company_link
    @Override
    @Transactional
    public CompanyResponseDTO approveOrRejectCompany(Long companyId, CompanyApproveDTO dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));

        if (dto.getApprove() != null && dto.getApprove()) {

            company.setStatus(CompanyStatus.APPROVED);

            // Se for aprovado e ainda não existir o vínculo, cria
            CompanyLink link = CompanyLink.builder()
                    .approved(true)
                    .roleInCompany(UserRole.OWNER)
                    .user(company.getOwner())
                    .company(company)
                    .active(true)
                    .build();

            companyLinkRepository.save(link);

        } else {
            company.setStatus(CompanyStatus.REJECTED);
        }

        companyRepository.save(company);
        return mapToDTO(company);
    }

    @Override
    public List<CompanyResponseDTO> getCompaniesByOwner() {
        User owner = SecurityUtils.getCurrentUserOrThrow(userRepository);
        return companyRepository.findAll()
                .stream()
                .filter(c -> c.getOwner() != null && c.getOwner().getId().equals(owner.getId()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponseDTO getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company não encontrada"));
        return mapToDTO(company);
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
