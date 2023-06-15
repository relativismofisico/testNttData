package com.nttdata.inventory.dto;

import javax.validation.constraints.NotBlank;

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
	
	private double priceEquipment;

}
