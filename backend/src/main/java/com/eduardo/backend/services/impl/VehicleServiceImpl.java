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
// Camada responsável pelas regras de negócio dos veículos
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

    // Criação de veículo vinculada à empresa do usuário logado
    @Override
    @Transactional
    public VehicleResponseDTO create(VehicleCreateDTO dto) {

        // Usuário sempre obtido via contexto de segurança
        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        // Usuário precisa ter vínculo aprovado com alguma empresa
        CompanyLink companyLink = companyLinkRepository
                .findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() ->
                        new BadRequestException("Usuário não possui vínculo aprovado com nenhuma empresa")
                );

        // Garante unicidade da placa
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

    // Lista todos os veículos da empresa do usuário
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

    // Atualiza dados de um veículo
    @Override
    @Transactional
    public VehicleResponseDTO update(Long id, VehicleCreateDTO dto) {

        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        // Garante que o veículo pertence ao usuário
        if (!vehicle.getCompanyLink().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Você não pode editar este veículo");
        }

        // Evita duplicidade de placa
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

    // Desativação lógica (soft delete)
    @Override
    @Transactional
    public void deactivate(Long vehicleId) {

        User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        // Confere posse do veículo
        if (!vehicle.getCompanyLink().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Você não pode desativar este veículo");
        }

        // Evita operação duplicada
        if (!vehicle.getActive()) {
            throw new BadRequestException("Veículo já está desativado");
        }

        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    // Conversão Entity → DTO
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
