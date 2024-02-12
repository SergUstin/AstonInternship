package com.campany.mapper;

import com.campany.dto.ManagerDTO;
import com.campany.entity.Manager;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerMapper {
    public static ManagerDTO toDTO(Manager manager) {
        return new ManagerDTO(manager.getId(), manager.getFullName(), manager.getSalary());
    }
    public static List<ManagerDTO> toDTOList(List<Manager> managers) {
        return managers.stream()
                .map(ManagerMapper::toDTO)
                .collect(Collectors.toList());
    }
    public static Manager toEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setId(managerDTO.getId());
        manager.setFullName(managerDTO.getFullName());
        manager.setSalary(managerDTO.getSalary());
        return manager;
    }
    public static List<Manager> toEntityList(List<ManagerDTO> managerDTOs) {
        return managerDTOs.stream()
                .map(ManagerMapper::toEntity)
                .collect(Collectors.toList());
    }
}
