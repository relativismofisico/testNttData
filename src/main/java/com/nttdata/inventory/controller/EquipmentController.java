package com.nttdata.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.inventory.dto.Converter;
import com.nttdata.inventory.dto.EquipmentDTORequest;
import com.nttdata.inventory.dto.EquipmentDTOResponse;
import com.nttdata.inventory.entity.Equipment;
import com.nttdata.inventory.exceptions.BadRequestException;
import com.nttdata.inventory.service.EquipmentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {

	@Autowired
	private EquipmentServiceImpl equipmentService;
	
	@Autowired
	private Converter converter;
	
	@PostMapping(value="/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createUser(@RequestBody EquipmentDTORequest equipmentDTORequest) {
		
		if(equipmentDTORequest.getSerialCodeEquipment().isEmpty()) {
			throw new BadRequestException("El serial del equipo es necesario para su creacion");
		}
		
		if(equipmentDTORequest.getNameEquipment().isEmpty()) {
			throw new BadRequestException("El nombre del equipo es necesario para su creacion");
		}
		
		if(equipmentDTORequest.getPriceEquipment()==0) {
			throw new BadRequestException("El precio del equipo es necesario para su creacion");
		}
		
		Equipment equipmentNew = equipmentService.insert(equipmentDTORequest);
		EquipmentDTOResponse equipmentDTOResponse = converter.equipmentToDTO(equipmentNew);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(equipmentDTOResponse);
	
	
	}
}
