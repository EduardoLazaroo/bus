package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.JourneyCreateDTO;
import com.eduardo.backend.dtos.JourneyResponseDTO;
import com.eduardo.backend.enums.JourneyStatus;
import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.models.*;
import com.eduardo.backend.repositories.*;
import com.eduardo.backend.services.JourneyService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JourneyServiceImpl implements JourneyService {

    private final JourneyRepository journeyRepository;
    private final CompanyLinkRepository companyLinkRepository;
    private final VehicleRepository vehicleRepository;
    private final JourneyDriverRepository journeyDriverRepository;
    private final JourneyClientRepository journeyClientRepository;
    private final JourneyVehicleRepository journeyVehicleRepository;
    private final com.eduardo.backend.repositories.UserRepository userRepository;

    public JourneyServiceImpl(
            JourneyRepository journeyRepository,
            CompanyLinkRepository companyLinkRepository,
            VehicleRepository vehicleRepository,
            JourneyDriverRepository journeyDriverRepository,
            JourneyClientRepository journeyClientRepository,
            JourneyVehicleRepository journeyVehicleRepository
            , com.eduardo.backend.repositories.UserRepository userRepository
    ) {
        this.journeyRepository = journeyRepository;
        this.companyLinkRepository = companyLinkRepository;
        this.vehicleRepository = vehicleRepository;
        this.journeyDriverRepository = journeyDriverRepository;
        this.journeyClientRepository = journeyClientRepository;
        this.journeyVehicleRepository = journeyVehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public JourneyResponseDTO create(JourneyCreateDTO dto) {
        var user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        // find user's approved company link
        CompanyLink companyLink = companyLinkRepository.findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() -> new BadRequestException("Usuário não possui vínculo aprovado"));

        Journey journey = Journey.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .companyLink(companyLink)
                .status(JourneyStatus.ACTIVE)
                .build();

        Journey saved = journeyRepository.save(journey);

        // drivers
        if (dto.getDriverCompanyLinkIds() != null) {
            dto.getDriverCompanyLinkIds().forEach(clId -> {
                var cl = companyLinkRepository.findById(clId)
                        .orElseThrow(() -> new BadRequestException("CompanyLink inválido: " + clId));
                JourneyDriver jd = JourneyDriver.builder()
                    .journey(saved)
                        .companyLink(cl)
                        .build();
                journeyDriverRepository.save(jd);
            });
        }

        // clients
        if (dto.getClientCompanyLinkIds() != null) {
            dto.getClientCompanyLinkIds().forEach(clId -> {
                var cl = companyLinkRepository.findById(clId)
                        .orElseThrow(() -> new BadRequestException("CompanyLink inválido: " + clId));
                JourneyClient jc = JourneyClient.builder()
                    .journey(saved)
                        .companyLink(cl)
                        .build();
                journeyClientRepository.save(jc);
            });
        }

        // vehicles
        if (dto.getVehicleIds() != null) {
            dto.getVehicleIds().forEach(vId -> {
                var v = vehicleRepository.findById(vId)
                        .orElseThrow(() -> new BadRequestException("Vehicle inválido: " + vId));
                JourneyVehicle jv = JourneyVehicle.builder()
                    .journey(saved)
                        .vehicle(v)
                        .build();
                journeyVehicleRepository.save(jv);
            });
        }

        return toDTO(saved);
    }

    @Override
    public List<JourneyResponseDTO> listMyCompanyJourneys() {
        var user = SecurityUtils.getCurrentUserOrThrow(userRepository);
        CompanyLink cl = companyLinkRepository.findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() -> new BadRequestException("Usuário não possui vínculo aprovado"));

        return journeyRepository.findByCompanyLinkId(cl.getId()).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public JourneyResponseDTO get(Long id) {
        Journey j = journeyRepository.findById(id).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));
        return toDTO(j);
    }

        @Override
        public com.eduardo.backend.dtos.JourneyOptionsDTO getOptions() {
        var user = SecurityUtils.getCurrentUserOrThrow(userRepository);
        CompanyLink cl = companyLinkRepository.findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
            .orElseThrow(() -> new BadRequestException("Usuário não possui vínculo aprovado"));

        Long companyId = cl.getCompany().getId();

        var approvedLinks = companyLinkRepository.findByCompanyIdAndStatus(companyId, LinkStatus.APPROVED);

        var drivers = approvedLinks.stream()
            .filter(l -> l.getRoleInCompany() == com.eduardo.backend.enums.UserRole.DRIVER)
            .map(l -> com.eduardo.backend.dtos.CompanyLinkResponseDTO.builder()
                .id(l.getId())
                .userId(l.getUser().getId())
                .userName(l.getUser().getName())
                .companyId(l.getCompany().getId())
                .companyName(l.getCompany().getCompanyName())
                .role(l.getRoleInCompany())
                .status(l.getStatus())
                .build())
            .collect(Collectors.toList());

        var clients = approvedLinks.stream()
            .filter(l -> l.getRoleInCompany() == com.eduardo.backend.enums.UserRole.CLIENT)
            .map(l -> com.eduardo.backend.dtos.CompanyLinkResponseDTO.builder()
                .id(l.getId())
                .userId(l.getUser().getId())
                .userName(l.getUser().getName())
                .companyId(l.getCompany().getId())
                .companyName(l.getCompany().getCompanyName())
                .role(l.getRoleInCompany())
                .status(l.getStatus())
                .build())
            .collect(Collectors.toList());

        var vehicles = vehicleRepository.findByCompanyLinkIdAndActiveTrue(cl.getId()).stream()
            .map(v -> com.eduardo.backend.dtos.VehicleResponseDTO.builder()
                .id(v.getId())
                .model(v.getModel())
                .licensePlate(v.getLicensePlate())
                .capacity(v.getCapacity())
                .type(v.getType())
                .year(v.getYear())
                .color(v.getColor())
                .active(v.getActive())
                .companyLinkId(v.getCompanyLink().getId())
                .build())
            .collect(Collectors.toList());

        return com.eduardo.backend.dtos.JourneyOptionsDTO.builder()
            .drivers(drivers)
            .clients(clients)
            .vehicles(vehicles)
            .build();
        }

    @Override
    @Transactional
    public JourneyResponseDTO update(Long id, JourneyCreateDTO dto) {
        Journey j = journeyRepository.findById(id).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));
        j.setName(dto.getName());
        j.setDescription(dto.getDescription());
        journeyRepository.save(j);
        return toDTO(j);
    }

    @Override
    public void deactivate(Long id) {
        Journey j = journeyRepository.findById(id).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));
        j.setStatus(JourneyStatus.INACTIVE);
        journeyRepository.save(j);
    }

    private JourneyResponseDTO toDTO(Journey j) {
        return JourneyResponseDTO.builder()
                .id(j.getId())
                .name(j.getName())
                .description(j.getDescription())
                .status(j.getStatus())
                .companyLinkId(j.getCompanyLink() != null ? j.getCompanyLink().getId() : null)
                .driverCompanyLinkIds(j.getDrivers() != null ? j.getDrivers().stream().map(d -> d.getCompanyLink().getId()).collect(Collectors.toList()) : null)
                .clientCompanyLinkIds(j.getClients() != null ? j.getClients().stream().map(c -> c.getCompanyLink().getId()).collect(Collectors.toList()) : null)
                .vehicleIds(j.getVehicles() != null ? j.getVehicles().stream().map(v -> v.getVehicle().getId()).collect(Collectors.toList()) : null)
                .build();
    }
}
