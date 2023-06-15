package com.nttdata.inventory.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="equipments")
@Data
@RequiredArgsConstructor 
@NoArgsConstructor
public class Equipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEquipment;
	
	@NonNull
	@Size(min = 4)
	private String serialCodeEquipment;
	
	@NonNull
	private String nameEquipment;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateBuyEquipment;
	private LocalDate dateUpDateEquipment;
	
	private double priceEquipment;	
	private double priceEquipmentDevaluation;

}
