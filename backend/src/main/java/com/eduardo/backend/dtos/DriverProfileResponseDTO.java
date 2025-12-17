package com.eduardo.backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DriverProfileResponseDTO {
	private Long id;
	private Long userId;
	private String userName;

	private String cpf;
	private String rg;
	private String cnhNumber;
	private String cnhCategory;
	private LocalDate cnhExpirationDate;
	private String cnhImage;
	private Boolean active;

}