package com.campany.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager {
    Integer id;
    String fullName;
    BigDecimal salary;
    List<Employee> employees;

    public Manager() {
    }

    public Manager(String fullName, BigDecimal salary, List<Employee> employees) {
        this.fullName = fullName;
        this.salary = salary;
        this.employees = employees;
    }

    public Manager(Integer id, String fullName, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
    }

    public Manager(Integer id, String fullName, BigDecimal salary, List<Employee> employees) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.employees = employees;
    }
}
