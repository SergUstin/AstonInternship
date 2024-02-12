package com.campany.dto;

import com.campany.entity.Manager;
import lombok.AccessLevel;
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
    Integer managerId;
    public EmployeeDTO() {
    }
    public EmployeeDTO(String fullName, BigDecimal salary, Integer manager) {
        this.fullName = fullName;
        this.salary = salary;
        this.managerId = manager;
    }
    public EmployeeDTO(Integer id, String fullName, BigDecimal salary, Integer manager) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.managerId = manager;
    }
}
