package com.campany.repository;

import com.campany.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements RepositoryMethod<Employee> {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/aston-test";
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
                                resultSet.getInt("manager_id"));
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
                            resultSet.getInt("manager_id"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    @Override
    public void save(Employee item) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO employees (id, full_name, salary, manager_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, item.getId());
                statement.setString(2, item.getFullName());
                statement.setBigDecimal(3, item.getSalary());
                statement.setInt(4, item.getManagerId());
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Employee item) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE employees SET full_name = ?, salary = ?, manager_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, item.getFullName());
                statement.setBigDecimal(2, item.getSalary());
                statement.setInt(3, item.getManagerId());
                statement.setInt(4, item.getId());
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteById(Integer id) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
