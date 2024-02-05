package com.campany.dto;

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
    String fillName;
    BigDecimal salary;
    ManagerDTO managerDTO;
}
