package com.nttdata.inventory.service;

import java.util.List;
import java.util.Optional;

import com.nttdata.inventory.dto.EquipmentDTORequest;
import com.nttdata.inventory.entity.Equipment;

public interface IEquipmentService {
	public Equipment insert(EquipmentDTORequest equipemntDTORequest);
	public Optional<Equipment> findBySerialCodeEquipment(String serialCodeEquipment);
	public List<Equipment> findAll();
	public Equipment upDateEquipment(EquipmentDTORequest equipemntDTORequest,String serialCodeEquipment );
	public void deleteEquipment(String serialCodeEquipment );

}
