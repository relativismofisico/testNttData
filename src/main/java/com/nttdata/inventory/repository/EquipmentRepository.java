package com.nttdata.inventory.repository;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nttdata.inventory.entity.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>{
	
	@Query(value="SELECT * FROM equipments WHERE SERIAL_CODE_EQUIPMENT =?1", nativeQuery = true)
	Optional<Equipment> findBySerialCodeEquipment(String serialCodeEquipment);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM equipments WHERE SERIAL_CODE_EQUIPMENT =?1 ", nativeQuery = true)
	public void deleteBySerialCodeEquipment(String serialCodeEquipment);

}
