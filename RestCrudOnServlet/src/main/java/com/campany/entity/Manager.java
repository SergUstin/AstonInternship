package com.campany.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager {
    Integer id;
    String fullName;
    BigDecimal salary;
    List<Employee> employees;
}
