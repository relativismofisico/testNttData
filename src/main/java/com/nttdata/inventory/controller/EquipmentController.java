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
import org.springframework.web.bind.annotation.PutMapping;
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

/*import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {

	@Autowired
	private EquipmentServiceImpl equipmentService;
	
	@Autowired
	private Converter converter;
	
	@Operation(summary = "Permite guardar un objeto equipo a traves del paso de un dto")
	/*@ApiResponses(value= {
			@ApiResponse(code = 201, message = "CREATED", response = EquipmentDTOResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = InternalServerErrorException.class)})*/
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
	
	@Operation(summary = "Permite listar todos los objetos equipos guardados hasta el momento en la BD")
	/*@ApiResponses(value= {
			@ApiResponse(code = 302, message = "FOUND", response = EquipmentDTOResponse.class),
			@ApiResponse(code = 204, message = "No Content", response = EquipmentDTOResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = InternalServerErrorException.class)})*/
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
	
	@Operation(summary = "Permite buscar un objeto equipo en particular a traves de su codigo de serie")
	/*@ApiResponses(value= {
			@ApiResponse(code = 302, message = "FOUND", response = EquipmentDTOResponse.class),
			@ApiResponse(code = 404, message = "No Found", response = ResourceNotFoundException.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = InternalServerErrorException.class)})*/
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
	
	@Operation(summary = "Permite eliminar un objeto equipo en particular a traves de su codigo de serie")
	/*@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK", response = EquipmentDTOResponse.class),
			@ApiResponse(code = 404, message = "No Found", response = EquipmentDTOResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = InternalServerErrorException.class)})
	@DeleteMapping(value="/delete/{serialCodeEquipment}")*/
	public ResponseEntity<?> deleteBySerialCodeEquipment(@PathVariable("serialCodeEquipment") String serialCodeEquipment){
		
		Optional<Equipment> optionalEquipmentDelete = equipmentService.findBySerialCodeEquipment(serialCodeEquipment);
		
		if(!optionalEquipmentDelete.isPresent()) {
			throw new ResourceNotFoundException("El Equipo no existe por tanto no puede ser eliminado");
		}else {
			this.equipmentService.deleteEquipment(serialCodeEquipment);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}
	}
	
	@Operation(summary = "Permite actualizar un objeto equipo en particular a traves de su codigo de serie y un dto con los campos del objeto")
	/*@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK", response = EquipmentDTOResponse.class),
			
			@ApiResponse(code = 500, message = "Internal server error", response = InternalServerErrorException.class)})*/
	@PutMapping(value="/upDate/{serialCodeEquipment}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> upDateEquipment(@PathVariable("serialCodeEquipment") String serialCodeEquipment, @RequestBody EquipmentDTORequest equipmentDTORequest){
		
		
		
		Equipment upDateEquipment = equipmentService.upDateEquipment(equipmentDTORequest, serialCodeEquipment);
		EquipmentDTOResponse equipmentUpDate = converter.equipmentToDTO(upDateEquipment);
		
		return ResponseEntity.status(HttpStatus.OK).body(equipmentUpDate);
	}
	
	
}
