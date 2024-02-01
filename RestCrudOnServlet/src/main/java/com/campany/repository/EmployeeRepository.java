package com.campany.repository;

import com.campany.entity.Employee;

import java.util.List;

public class EmployeeRepository implements RepositoryMethod<Employee> {
    @Override
    public Employee findById(Integer id) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public void create(Employee item) {

    }

    @Override
    public void update(Employee item) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
