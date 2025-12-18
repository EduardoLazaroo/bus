package com.eduardo.backend.repositories;

import com.eduardo.backend.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Verifica se já existe veículo com a mesma placa
    boolean existsByLicensePlate(String licensePlate);

    // Verifica placa duplicada ignorando o próprio registro (update)
    boolean existsByLicensePlateAndIdNot(String licensePlate, Long id);

    // Lista todos os veículos de um vínculo empresa-usuário
    List<Vehicle> findByCompanyLinkId(Long companyLinkId);

    // Lista apenas veículos ativos de um vínculo
    List<Vehicle> findByCompanyLinkIdAndActiveTrue(Long companyLinkId);

    boolean existsByCompanyLinkIdAndActiveTrue(Long companyLinkId);
}
