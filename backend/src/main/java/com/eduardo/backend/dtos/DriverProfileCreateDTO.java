package com.eduardo.backend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverProfileCreateDTO {
	private String cpf;
	private String rg;
	private String cnhNumber;
	private String cnhCategory;
	private LocalDate cnhExpirationDate;
	private String cnhImage;
}