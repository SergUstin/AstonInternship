package com.campany.repository;

import com.campany.entity.Employee;
import com.campany.entity.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements RepositoryMethod<Employee> {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    @Override
    public Employee findById(Integer id) {
        Employee employee = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        employee = new Employee(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getBigDecimal("salary"),
                                getManagersByEmployeesId(resultSet.getInt("id")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM employees";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee(resultSet.getInt("id"),
                            resultSet.getString("full_name"),
                            resultSet.getBigDecimal("salary"),
                            getManagersByEmployeesId(resultSet.getInt("id")));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void create(Employee item) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, item.getId());
                statement.setString(2, item.getFullName());
                statement.setBigDecimal(3, item.getSalary());
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee item) {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE managers SET full_name = ?, salary = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(2, item.getFullName());
                statement.setBigDecimal(3, item.getSalary());
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM managers WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Manager getManagersByEmployeesId(Integer employeeId) {
        Manager manager = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM managers WHERE manager_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        manager = new Manager(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getBigDecimal("salary"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }
}
