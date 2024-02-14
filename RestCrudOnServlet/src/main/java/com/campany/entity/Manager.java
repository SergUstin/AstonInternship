package com.campany.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Manager manager)) {
            return false;
        }
        return this.id.equals(manager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
