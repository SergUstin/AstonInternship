package com.campany.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO {
    Integer id;
    String fullName;
    BigDecimal salary;
    ManagerDTO managerDTO;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Integer id, String fullName, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
    }

    public EmployeeDTO(String fullName, BigDecimal salary, ManagerDTO managerDTO) {
        this.fullName = fullName;
        this.salary = salary;
        this.managerDTO = managerDTO;
    }

    public EmployeeDTO(Integer id, String fullName, BigDecimal salary, ManagerDTO managerDTO) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.managerDTO = managerDTO;
    }
}
