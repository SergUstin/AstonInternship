package com.campany.mapper;

import com.campany.dto.EmployeeDTO;
import com.campany.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getFullName(), employee.getSalary(), ManagerMapper.toDTO(employee.getManager()));
    }

    public static List<EmployeeDTO> toDTOList(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFullName(employeeDTO.getFullName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setManager(ManagerMapper.toEntity(employeeDTO.getManagerDTO()));
        return employee;
    }

    public static List<Employee> toEntityList(List<EmployeeDTO> employeeDTOs) {
        return employeeDTOs.stream()
                .map(EmployeeMapper::toEntity)
                .collect(Collectors.toList());
    }
}
