package com.campany.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ManagerDTO other = (ManagerDTO) obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.fullName, other.fullName) &&
                Objects.equals(this.salary, other.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, salary);
    }
}
