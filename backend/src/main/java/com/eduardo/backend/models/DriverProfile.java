package com.eduardo.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "driver_profiles")
// Entidade que representa o perfil de motorista no banco
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Relação 1–1 com usuário (um usuário tem no máximo um perfil de motorista)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	// Documentos pessoais
	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false)
	private String rg;

	// Dados da CNH
	@Column(name = "cnh_number", nullable = false, unique = true)
	private String cnhNumber;

	@Column(name = "cnh_category", nullable = false)
	private String cnhCategory;

	@Column(name = "cnh_expiration_date", nullable = false)
	private LocalDate cnhExpirationDate;

	// Imagem ou referência da CNH
	@Column(name = "cnh_image")
	private String cnhImage;

	// Flag lógica para ativação/desativação do perfil
	@Builder.Default
	private Boolean active = true;

	// Data de criação (imutável)
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	// Preenche automaticamente a data de criação
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
}
