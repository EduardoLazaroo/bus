package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.VehicleCreateDTO;
import com.eduardo.backend.dtos.VehicleResponseDTO;
import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.exceptions.ResourceNotFoundException;
import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.models.User;
import com.eduardo.backend.models.Vehicle;
import com.eduardo.backend.repositories.CompanyLinkRepository;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.repositories.VehicleRepository;
import com.eduardo.backend.services.VehicleService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyLinkRepository companyLinkRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(
            VehicleRepository vehicleRepository,
            CompanyLinkRepository companyLinkRepository,
            UserRepository userRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.companyLinkRepository = companyLinkRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public VehicleResponseDTO create(VehicleCreateDTO dto) {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        CompanyLink companyLink = companyLinkRepository
                .findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() ->
                        new BadRequestException("Usuário não possui vínculo aprovado com nenhuma empresa")
                );

        if (vehicleRepository.existsByLicensePlate(dto.getLicensePlate())) {
            throw new BadRequestException("Placa já cadastrada");
        }

        Vehicle vehicle = Vehicle.builder()
                .model(dto.getModel())
                .licensePlate(dto.getLicensePlate())
                .capacity(dto.getCapacity())
                .type(dto.getType())
                .year(dto.getYear())
                .color(dto.getColor())
                .active(true)
                .companyLink(companyLink)
                .build();

        vehicleRepository.save(vehicle);
        return mapToDTO(vehicle);
    }

    @Override
    public List<VehicleResponseDTO> getMyVehicles() {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        CompanyLink companyLink = companyLinkRepository
                .findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() ->
                        new BadRequestException("Usuário não possui vínculo aprovado com nenhuma empresa")
                );

        return vehicleRepository
                .findByCompanyLinkId(companyLink.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional
    public VehicleResponseDTO update(Long id, VehicleCreateDTO dto) {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        if (!vehicle.getCompanyLink().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Você não pode editar este veículo");
        }

        if (vehicleRepository.existsByLicensePlateAndIdNot(dto.getLicensePlate(), id)) {
            throw new BadRequestException("Placa já cadastrada em outro veículo");
        }

        vehicle.setModel(dto.getModel());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setCapacity(dto.getCapacity());
        vehicle.setType(dto.getType());
        vehicle.setYear(dto.getYear());
        vehicle.setColor(dto.getColor());

        vehicleRepository.save(vehicle);
        return mapToDTO(vehicle);
    }

    @Override
    @Transactional
    public void deactivate(Long vehicleId) {
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        if (!vehicle.getCompanyLink().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Você não pode desativar este veículo");
        }

        if (!vehicle.getActive()) {
            throw new BadRequestException("Veículo já está desativado");
        }

        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    private VehicleResponseDTO mapToDTO(Vehicle vehicle) {
        return VehicleResponseDTO.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .licensePlate(vehicle.getLicensePlate())
                .capacity(vehicle.getCapacity())
                .type(vehicle.getType())
                .year(vehicle.getYear())
                .color(vehicle.getColor())
                .active(vehicle.getActive())
                .companyLinkId(vehicle.getCompanyLink().getId())
                .build();
    }
}
