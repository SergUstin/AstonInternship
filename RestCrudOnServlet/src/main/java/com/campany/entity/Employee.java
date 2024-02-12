package com.campany.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    Integer id;
    String fullName;
    BigDecimal salary;
    Integer managerId;
    public Employee() {
    }
    public Employee(Integer id, String fullName, BigDecimal salary, Integer manager) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.managerId = manager;
    }
}
