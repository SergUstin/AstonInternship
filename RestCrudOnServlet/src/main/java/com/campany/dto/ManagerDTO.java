package com.campany.dto;

import lombok.AccessLevel;
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
    String fillName;
    BigDecimal salary;
    List<EmployeeDTO> employeeDTOS;
}
