package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.DriverProfileCreateDTO;
import com.eduardo.backend.dtos.DriverProfileResponseDTO;
import com.eduardo.backend.exceptions.ResourceNotFoundException;
import com.eduardo.backend.models.DriverProfile;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.DriverProfileRepository;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.DriverProfileService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// Camada responsável pela regra de negócio do perfil de motorista
public class DriverProfileServiceImpl implements DriverProfileService {

	private final DriverProfileRepository driverProfileRepository;
	private final UserRepository userRepository;

	public DriverProfileServiceImpl(
			DriverProfileRepository driverProfileRepository,
			UserRepository userRepository
	) {
		this.driverProfileRepository = driverProfileRepository;
		this.userRepository = userRepository;
	}

	// Cria ou atualiza o perfil do motorista do usuário logado
	@Override
	@Transactional
	public DriverProfileResponseDTO createOrUpdate(DriverProfileCreateDTO dto) {

		// Usuário obtido exclusivamente do contexto de segurança (JWT)
		User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

		// Se já existir perfil, reutiliza; senão cria um novo
		DriverProfile profile = driverProfileRepository
				.findByUserId(user.getId())
				.orElse(
						DriverProfile.builder()
								.user(user)
								.build()
				);

		// Atualização dos dados do perfil
		profile.setCpf(dto.getCpf());
		profile.setRg(dto.getRg());
		profile.setCnhNumber(dto.getCnhNumber());
		profile.setCnhCategory(dto.getCnhCategory());
		profile.setCnhExpirationDate(dto.getCnhExpirationDate());
		profile.setCnhImage(dto.getCnhImage());

		driverProfileRepository.save(profile);

		return mapToDTO(profile);
	}

	// Retorna o perfil do motorista do usuário autenticado
	@Override
	public DriverProfileResponseDTO getMyProfile() {

		User user = SecurityUtils.getCurrentUserOrThrow(userRepository);

		DriverProfile profile = driverProfileRepository
				.findByUserId(user.getId())
				.orElseThrow(() ->
						new ResourceNotFoundException("Perfil de motorista não encontrado")
				);

		return mapToDTO(profile);
	}

	// Converte entidade em DTO de resposta
	private DriverProfileResponseDTO mapToDTO(DriverProfile profile) {
		return DriverProfileResponseDTO.builder()
				.id(profile.getId())
				.userId(profile.getUser().getId())
				.userName(profile.getUser().getName())
				.cpf(profile.getCpf())
				.rg(profile.getRg())
				.cnhNumber(profile.getCnhNumber())
				.cnhCategory(profile.getCnhCategory())
				.cnhExpirationDate(profile.getCnhExpirationDate())
				.cnhImage(profile.getCnhImage())
				.active(profile.getActive())
				.build();
	}
}