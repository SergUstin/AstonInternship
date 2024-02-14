package com.campany.mapper;

import com.campany.dto.ManagerDTO;
import com.campany.entity.Manager;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ManagerMapperTest {
    @Test
    public void testToDTO() {
        Manager manager = new Manager();
        manager.setId(1);
        manager.setFullName("John Smith");
        manager.setSalary(new BigDecimal(50000));

        ManagerDTO managerDTO = ManagerMapper.toDTO(manager);

        assertEquals(manager.getId(), managerDTO.getId());
        assertEquals(manager.getFullName(), managerDTO.getFullName());
        assertEquals(manager.getSalary(), managerDTO.getSalary());
    }

    @Test
    public void testToDTOList() {
        List<Manager> managers = Arrays.asList(
                new Manager(1, "John Smith", new BigDecimal(50000)),
                new Manager(2, "Jane Doe", new BigDecimal(60000))
        );

        List<ManagerDTO> managerDTOs = ManagerMapper.toDTOList(managers);

        assertEquals(2, managerDTOs.size());
        assertEquals("John Smith", managerDTOs.get(0).getFullName());
        assertEquals(new BigDecimal(50000), managerDTOs.get(0).getSalary());
        assertEquals("Jane Doe", managerDTOs.get(1).getFullName());
        assertEquals(new BigDecimal(60000), managerDTOs.get(1).getSalary());
    }

    @Test
    public void testToEntity() {
        ManagerDTO managerDTO = new ManagerDTO(1, "John Smith", new BigDecimal(50000));

        Manager manager = ManagerMapper.toEntity(managerDTO);

        assertEquals(managerDTO.getId(), manager.getId());
        assertEquals(managerDTO.getFullName(), manager.getFullName());
        assertEquals(managerDTO.getSalary(), manager.getSalary());
    }

    @Test
    public void testToEntityList() {
        List<ManagerDTO> managerDTOs = Arrays.asList(
                new ManagerDTO(1, "John Smith", new BigDecimal(50000)),
                new ManagerDTO(2, "Jane Doe", new BigDecimal(60000))
        );

        List<Manager> managers = ManagerMapper.toEntityList(managerDTOs);

        assertEquals(2, managers.size());
        assertEquals(managerDTOs.get(0).getId(), managers.get(0).getId());
        assertEquals(managerDTOs.get(0).getFullName(), managers.get(0).getFullName());
        assertEquals(managerDTOs.get(0).getSalary(), managers.get(0).getSalary());
        assertEquals(managerDTOs.get(1).getId(), managers.get(1).getId());
        assertEquals(managerDTOs.get(1).getFullName(), managers.get(1).getFullName());
        assertEquals(managerDTOs.get(1).getSalary(), managers.get(1).getSalary());
    }

}