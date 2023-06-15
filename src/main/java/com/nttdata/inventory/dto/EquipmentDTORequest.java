package com.nttdata.inventory.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTORequest {
	
	@NonNull
	@NotBlank(message = "El serial del equipo es obligatorio")
	private String serialCodeEquipment;
	
	@NonNull
	private String nameEquipment;
	
	private String descriptionEquipment;	
	private double priceEquipment;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateBuyEquipment;

}
