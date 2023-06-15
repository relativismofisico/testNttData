package com.nttdata.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.inventory.dto.Converter;
import com.nttdata.inventory.dto.EquipmentDTORequest;
import com.nttdata.inventory.entity.Equipment;
import com.nttdata.inventory.exceptions.ResourceNotFoundException;
import com.nttdata.inventory.repository.EquipmentRepository;

@Service
@Transactional
public class EquipmentServiceImpl implements IEquipmentService{
	
	@Autowired
	private Converter converter;
	
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public Equipment insert(EquipmentDTORequest equipemntDTORequest) {
		
		Equipment equipmentNew = converter.equipmentDTORequestToEquipment(equipemntDTORequest);
		
		equipmentNew.setDateBuyEquipment(LocalDate.now());
		equipmentNew.setPriceEquipmentDevaluation(equipmentNew.getPriceEquipment());
		
		equipmentRepository.save(equipmentNew);
		
		return equipmentNew;
	}

	@Override
	public Optional<Equipment> findBySerialCodeEquipment(String serialCodeEquipment) {
		return equipmentRepository.findBySerialCodeEquipment(serialCodeEquipment);
	}

	@Override
	public List<Equipment> findAll() {
		List<Equipment> equipmentsList = new ArrayList<>();
		equipmentRepository.findAll().forEach(equipment->equipmentsList.add(equipment));
		return equipmentsList;
	}

	@Override
	public Equipment upDateEquipment(EquipmentDTORequest equipemntDTORequest, String serialCodeEquipment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEquipment(String serialCodeEquipment) {
		equipmentRepository.deleteBySerialCodeEquipment(serialCodeEquipment);
	}

}
