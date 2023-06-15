package com.nttdata.inventory.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EquipmentDTOResponse {

	private String serialCodeEquipment;
	private String nameEquipment;
	private LocalDate dateBuyEquipment;
	private double priceEquipment;
	private double priceEquipmentDevaluation;
}
