package com.eduardo.backend.repositories;

import com.eduardo.backend.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // placa única
    boolean existsByLicensePlate(String licensePlate);

    // placa única ao atualizar
    boolean existsByLicensePlateAndIdNot(String licensePlate, Long id);

    // veículos de uma empresa
    List<Vehicle> findByCompanyLinkId(Long companyLinkId);

    // veículos ativos de uma empresa
    List<Vehicle> findByCompanyLinkIdAndActiveTrue(Long companyLinkId);
}
