package com.nttdata.inventory.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.inventory.dto.Converter;
import com.nttdata.inventory.dto.EquipmentDTORequest;
import com.nttdata.inventory.entity.Equipment;
import com.nttdata.inventory.exceptions.InternalServerErrorException;
import com.nttdata.inventory.repository.EquipmentRepository;
import com.nttdata.inventory.util.Properties;

@Service
@Transactional
public class EquipmentServiceImpl implements IEquipmentService{
	
	@Autowired
	private Properties properties;
	
	@Autowired
	private Converter converter;
	
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public Equipment insert(EquipmentDTORequest equipemntDTORequest) {
		
		Equipment equipmentNew = converter.equipmentDTORequestToEquipment(equipemntDTORequest);
		
		equipmentNew.setPriceEquipmentDevaluation(equipmentNew.getPriceEquipment());
		
		Period period = Period.between(equipmentNew.getDateBuyEquipment(), LocalDate.now());
		
		if(period.getYears()> properties.getYearsDepression()) {
			  equipmentNew.setPriceEquipmentDevaluation(this.priceEquipmentDepression(period.getYears(), 
					  equipmentNew.getPriceEquipment()));
			}else {
				equipmentNew.setPriceEquipmentDevaluation(equipmentNew.getPriceEquipment());
			}
		
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
		
	Optional<Equipment> equipmentUpDateNew= equipmentRepository.findBySerialCodeEquipment(serialCodeEquipment);
	
		
		if(equipmentUpDateNew.isPresent()) {
			
			Equipment equipmentUpDateN = equipmentUpDateNew.get();
			Equipment equipmentUpDate = converter.equipmentDTORequestToEquipment(equipemntDTORequest);
			
			equipmentUpDateN.setNameEquipment(equipmentUpDate.getNameEquipment());
			equipmentUpDateN.setDescriptionEquipment(equipmentUpDate.getDescriptionEquipment());
			equipmentUpDateN.setDateBuyEquipment(equipmentUpDateN.getDateBuyEquipment());
			equipmentUpDateN.setDateUpDateEquipment(LocalDate.now());
			equipmentUpDateN.setPriceEquipment(equipmentUpDate.getPriceEquipment());
			
			Period period = Period.between(equipmentUpDateN.getDateBuyEquipment(), LocalDate.now());
			
			if(period.getYears()> properties.getYearsDepression()) {
			  equipmentUpDateN.setPriceEquipmentDevaluation(this.priceEquipmentDepression(period.getYears(), 
					  equipmentUpDateN.getPriceEquipment()));
			}else {
				equipmentUpDateN.setPriceEquipmentDevaluation(equipmentUpDateN.getPriceEquipment());
			}
			
			return equipmentRepository.save(equipmentUpDateN);
			
		}else {
			throw new InternalServerErrorException("El equipo no existe, por tanto no se puede actualizar");
		}
	}

	@Override
	public void deleteEquipment(String serialCodeEquipment) {
		equipmentRepository.deleteBySerialCodeEquipment(serialCodeEquipment);
	}
	
	public double priceEquipmentDepression(Integer years, double priceEquipment) {
		
		double priceEquiDepre = priceEquipment - ((properties.getLostPercentage()/100)*(years))*priceEquipment; 
		return priceEquiDepre;
	}
	
	

}
