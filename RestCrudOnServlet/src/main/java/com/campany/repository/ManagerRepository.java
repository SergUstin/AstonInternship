package com.campany.repository;

import com.campany.entity.Employee;
import com.campany.entity.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerRepository implements RepositoryMethod<Manager> {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    @Override
    public Manager findById(Integer id) {
        Manager manager = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM manager WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        manager = new Manager(resultSet.getInt("id"), resultSet.getString("full_name"),
                                resultSet.getBigDecimal("salary"),
                                getEmployeesByManagerId(resultSet.getInt("id")));
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> managers = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM manager";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Manager manager = new Manager(resultSet.getInt("id"),
                            resultSet.getString("full_name"),
                            resultSet.getBigDecimal("salary"),
                            getEmployeesByManagerId(resultSet.getInt("id")));
                    managers.add(manager);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }

    @Override
    public void create(Manager item) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.setString(2, item.getFullName());
                preparedStatement.setBigDecimal(3, item.getSalary());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Manager item) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE managers SET full_name = ?, salary = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(3, item.getId());
                statement.setString(1, item.getFullName());
                statement.setBigDecimal(2, item.getSalary());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Employee> getEmployeesByManagerId(Integer managerId) {
        List<Employee> employees = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM employees WHERE manager_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, managerId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Employee employee = new Employee(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getBigDecimal("salary"));
                        employees.add(employee);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
