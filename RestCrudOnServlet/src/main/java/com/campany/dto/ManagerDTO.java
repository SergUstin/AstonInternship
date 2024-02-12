package com.campany.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerDTO {
    Integer id;
    String fullName;
    BigDecimal salary;
    public ManagerDTO() {
    }
    public ManagerDTO(Integer id, String fullName, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
    }

    public ManagerDTO(String fullName, BigDecimal salary) {
        this.fullName = fullName;
        this.salary = salary;
    }
}
