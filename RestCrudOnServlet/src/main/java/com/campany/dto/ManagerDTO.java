package com.campany.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerDTO {
    Integer id;
    String fullName;
    BigDecimal salary;
    List<EmployeeDTO> employeeDTOS;

    public ManagerDTO() {
    }

    public ManagerDTO(Integer id, String fullName, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
    }

    public ManagerDTO(String fullName, BigDecimal salary, List<EmployeeDTO> employeeDTOS) {
        this.fullName = fullName;
        this.salary = salary;
        this.employeeDTOS = employeeDTOS;
    }

    public ManagerDTO(Integer id, String fullName, BigDecimal salary, List<EmployeeDTO> employeeDTOS) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.employeeDTOS = employeeDTOS;
    }
}
