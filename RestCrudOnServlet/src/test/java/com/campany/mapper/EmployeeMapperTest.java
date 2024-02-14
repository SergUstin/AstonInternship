package com.campany.mapper;

import com.campany.dto.EmployeeDTO;
import com.campany.entity.Employee;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeMapperTest {
    @Test
    public void testToDTO() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFullName("John Smith");
        employee.setSalary(new BigDecimal(50000));
        employee.setManagerId(1);

        EmployeeDTO employeeDTO = EmployeeMapper.toDTO(employee);

        assertEquals(employee.getId(), employeeDTO.getId());
        assertEquals(employee.getFullName(), employeeDTO.getFullName());
        assertEquals(employee.getSalary(), employeeDTO.getSalary());
        assertEquals(employee.getManagerId(), employeeDTO.getManagerId());
    }

    @Test
    public void testToDTOList() {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "John Smith", new BigDecimal(50000), 1),
                new Employee(2, "Jane Doe", new BigDecimal(60000), 2)
        );

        List<EmployeeDTO> employeeDTOs = EmployeeMapper.toDTOList(employees);

        assertEquals(2, employeeDTOs.size());

        EmployeeDTO employeeDTO1 = employeeDTOs.get(0);
        assertEquals("John Smith", employeeDTO1.getFullName());
        assertEquals(new BigDecimal(50000), employeeDTO1.getSalary());

        EmployeeDTO employeeDTO2 = employeeDTOs.get(1);
        assertEquals("Jane Doe", employeeDTO2.getFullName());
        assertEquals(new BigDecimal(60000), employeeDTO2.getSalary());
    }

    @Test
    public void testToEntity() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John Smith", new BigDecimal(5000), 1);

        Employee employee = EmployeeMapper.toEntity(employeeDTO);

        assertEquals(employeeDTO.getId(), employee.getId());
        assertEquals(employeeDTO.getFullName(), employee.getFullName());
        assertEquals(employeeDTO.getSalary(), employee.getSalary());
        assertEquals(employeeDTO.getManagerId(), employee.getManagerId());
    }

    @Test
    public void testToEntityList() {
        List<EmployeeDTO> employeeDTOs = Arrays.asList(
                new EmployeeDTO(1, "John Smith", new BigDecimal(50000), 1),
                new EmployeeDTO(2, "Jane Doe", new BigDecimal(60000), 2)
        );

        List<Employee> employees = EmployeeMapper.toEntityList(employeeDTOs);

        assertEquals(2, employees.size());

        Employee employee1 = employees.get(0);
        assertEquals("John Smith", employee1.getFullName());
        assertEquals(new BigDecimal(50000), employee1.getSalary());

        Employee employee2 = employees.get(1);
        assertEquals("Jane Doe", employee2.getFullName());
        assertEquals(new BigDecimal(60000), employee2.getSalary());
    }

}