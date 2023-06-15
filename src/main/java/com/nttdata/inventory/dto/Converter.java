package com.nttdata.inventory.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nttdata.inventory.entity.Equipment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class Converter {
	
private final ModelMapper modelMapper;
	
	public EquipmentDTOResponse equipmentToDTO (Equipment equipment) {
        return modelMapper.map(equipment, EquipmentDTOResponse.class);
    } 
	
	public Equipment equipmentDTORequestToEquipment (EquipmentDTORequest equipmentDTORequest) {
        return modelMapper.map(equipmentDTORequest, Equipment.class);
    }

}
