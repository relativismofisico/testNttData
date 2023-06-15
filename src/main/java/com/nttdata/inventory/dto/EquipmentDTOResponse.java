package com.nttdata.inventory.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EquipmentDTOResponse {

	private String serialCodeEquipment;
	private String nameEquipment;
	private String descriptionEquipment;
	private LocalDate dateBuyEquipment;
	private LocalDate dateUpDateEquipment;
	private double priceEquipment;
	private double priceEquipmentDevaluation;
}
