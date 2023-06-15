package com.nttdata.inventory.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nttdata.inventory.dto.Converter;
import com.nttdata.inventory.dto.EquipmentDTORequest;
import com.nttdata.inventory.dto.EquipmentDTOResponse;
import com.nttdata.inventory.entity.Equipment;
import com.nttdata.inventory.exceptions.BadRequestException;
import com.nttdata.inventory.exceptions.InternalServerErrorException;
import com.nttdata.inventory.exceptions.ResourceNotFoundException;
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
		if(equipmentService.findBySerialCodeEquipment(equipmentDTORequest.getSerialCodeEquipment()).isPresent()) {
			throw new InternalServerErrorException("El equipo ya se encuentra registrado");
		}
		
		Equipment equipmentNew = equipmentService.insert(equipmentDTORequest);
		EquipmentDTOResponse equipmentDTOResponse = converter.equipmentToDTO(equipmentNew);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(equipmentDTOResponse);
	}
	
	@GetMapping(value="/findAll", produces="application/json")
	public ResponseEntity<?> findAllEquipment(){
		
		List<Equipment> equipmentList = equipmentService.findAll();
		
		if(equipmentList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}else {
			List<EquipmentDTOResponse> equipmentDTOList = equipmentList.stream()
					.map(converter::equipmentToDTO)
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.FOUND).body(equipmentDTOList);
		}
	}
	
	@GetMapping(value="/findByCode/{serialCodeEquipment}", produces="application/json")
	public ResponseEntity<?> findBySerialCodeEquipment(@PathVariable("serialCodeEquipment") String serialCodeEquipment){
		
		Optional<Equipment> optionalEquipment = equipmentService.findBySerialCodeEquipment(serialCodeEquipment);
		
		if(!optionalEquipment.isPresent()) {
			throw new ResourceNotFoundException("El Equipo No existe");
		}
		
		Equipment equipment = optionalEquipment.get();
		EquipmentDTOResponse equipmentDTOResponse = converter.equipmentToDTO(equipment);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(equipmentDTOResponse);
	}
	
	@DeleteMapping(value="/delete/{serialCodeEquipment}")
	public ResponseEntity<?> deleteBySerialCodeEquipment(@PathVariable("serialCodeEquipment") String serialCodeEquipment){
		
		Optional<Equipment> optionalEquipmentDelete = equipmentService.findBySerialCodeEquipment(serialCodeEquipment);
		
		if(!optionalEquipmentDelete.isPresent()) {
			throw new ResourceNotFoundException("El Equipo no existe por tanto no puede ser eliminado");
		}else {
			this.equipmentService.deleteEquipment(serialCodeEquipment);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}
	}
	
	
}
