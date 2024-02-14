package com.campany.dto;

import com.campany.entity.Employee;
import com.campany.entity.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EmployeeDTO other = (EmployeeDTO) obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.fullName, other.fullName) &&
                Objects.equals(this.salary, other.salary) &&
                Objects.equals(this.managerId, other.managerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, salary, managerId);
    }
}
