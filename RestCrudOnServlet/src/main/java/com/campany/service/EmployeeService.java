package com.campany.service;

import com.campany.dto.EmployeeDTO;
import com.campany.entity.Employee;
import com.campany.mapper.EmployeeMapper;
import com.campany.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService implements CrudService<EmployeeDTO>{
    private final EmployeeRepository employeeRepository;
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
    public EmployeeDTO create(EmployeeDTO item) throws ClassNotFoundException {
        Employee employee = EmployeeMapper.toEntity(item);
        employeeRepository.save(employee);
        return EmployeeMapper.toDTO(employee);
    }
    @Override
    public EmployeeDTO update(Integer id, EmployeeDTO item) throws ClassNotFoundException {
        item.setId(id);
        Employee employee = EmployeeMapper.toEntity(item);
        employeeRepository.update(employee);
        return EmployeeMapper.toDTO(employee);
    }
    @Override
    public void deleteById(Integer id) throws ClassNotFoundException {
        employeeRepository.deleteById(id);
    }
}
