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
    Manager manager;

    public Employee() {
    }

    public Employee(Integer id, String fullName, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
    }

    public Employee(Integer id, String fullName, BigDecimal salary, Manager manager) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.manager = manager;
    }
}
