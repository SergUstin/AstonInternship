package com.campany.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager {
    Integer id;
    String fullName;
    BigDecimal salary;
    public Manager() {
    }
    public Manager(Integer id, String fullName, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
    }
}
