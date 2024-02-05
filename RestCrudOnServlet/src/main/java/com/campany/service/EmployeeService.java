package com.campany.service;

import com.campany.dto.EmployeeDTO;
import com.campany.entity.Employee;
import com.campany.mapper.EmployeeMapper;
import com.campany.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService implements CrudService<EmployeeDTO>{

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO getById(Integer id) {
        Employee employee = employeeRepository.findById(id);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return EmployeeMapper.toDTOList(employees);
    }

    @Override
    public void create(EmployeeDTO item) {
        employeeRepository.create(EmployeeMapper.toEntity(item));
    }

    @Override
    public void update(EmployeeDTO item) {
        employeeRepository.update(EmployeeMapper.toEntity(item));
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }
}
