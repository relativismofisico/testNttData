package com.nttdata.inventory.service;

import com.nttdata.inventory.dto.Converter;
import com.nttdata.inventory.dto.EquipmentDTORequest;
import com.nttdata.inventory.entity.Equipment;
import com.nttdata.inventory.repository.EquipmentRepository;
import com.nttdata.inventory.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EquipmentServiceImplTest {

    @Mock
    private Properties properties;

    @Mock
    private Converter converter;

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentServiceImpl equipmentService;

    private Equipment equipment;
    
    private EquipmentDTORequest equipmentDTORequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        equipment = new Equipment();
        equipment.setIdEquipment(1L);
        equipment.setSerialCodeEquipment("CODPRUEBA01");
        equipment.setNameEquipment("PC1Prueba");
        equipment.setDescriptionEquipment("Equipo de Pruebas");
        equipment.setPriceEquipment(200000);
        equipment.setDateBuyEquipment(LocalDate.now());
        
        equipmentDTORequest=new EquipmentDTORequest();
        equipmentDTORequest.setSerialCodeEquipment("CODPRUEBA01");
        equipmentDTORequest.setNameEquipment("PC1Prueba");
        equipmentDTORequest.setDescriptionEquipment("Equipo de Pruebas");
        equipmentDTORequest.setPriceEquipment(200000);
        equipmentDTORequest.setDateBuyEquipment(LocalDate.now());
        
        
    }

    @Test
    void findAll() {
        when(equipmentRepository.findAll()).thenReturn(Arrays.asList(equipment));
        assertNotNull(equipmentService.findAll());
    }
    
    @Test
    void save() {
    	when(converter.equipmentDTORequestToEquipment(equipmentDTORequest)).thenReturn(equipment);
    	when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
    	assertNotNull(equipmentService.insert(equipmentDTORequest));
    }
    
}