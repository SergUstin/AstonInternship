package com.campany.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    Integer id;
    String fullName;
    BigDecimal salary;
    Integer managerId;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Employee employee)) {
            return false;
        }
        return this.id.equals(employee.id);
    }
}
