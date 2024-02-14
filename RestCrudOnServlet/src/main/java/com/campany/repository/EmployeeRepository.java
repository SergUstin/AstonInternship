package com.campany.repository;

import com.campany.entity.Employee;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmployeeRepository implements RepositoryMethod<Employee> {
    private final ConnectionToBase connectionToBase;

    public EmployeeRepository(ConnectionToBase connectionToBase) {
        this.connectionToBase = connectionToBase;
    }

    @Override
    public Employee findById(Integer id) {
        log.info("Поиск сотрудника по ID: {}", id);
        Employee employee = null;
        try (Connection connection = connectionToBase.getConnection()) {
            String sql = "SELECT id, full_name, salary, manager_id FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        employee = new Employee(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getBigDecimal("salary"),
                                resultSet.getInt("manager_id"));
                        log.info("Найден сотрудник с ID {}: {}", id, employee);
                    } else {
                        log.info("Сотрудник с ID {} не найден", id);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при выполнении поиска сотрудника по ID {}", id, e);
        }
        return employee;
    }
    @Override
    public List<Employee> findAll() {
        log.info("Получение всех сотрудников");
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionToBase.getConnection()) {
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
            log.info("Получено {} сотрудников", employees.size());
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при получении сотрудников", e);
        }
        return employees;
    }
    @Override
    public void save(Employee item) {
        log.info("Сохранение сотрудника: {}", item.getFullName());
        try (Connection connection = connectionToBase.getConnection()) {
            String sql = "INSERT INTO employees (id, full_name, salary, manager_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, item.getId());
                statement.setString(2, item.getFullName());
                statement.setBigDecimal(3, item.getSalary());
                statement.setInt(4, item.getManagerId());
                statement.executeUpdate();
            }
            log.info("Сотрудник {} успешно сохранен", item.getFullName());
        }catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при сохранении сотрудника", e);
        }
    }
    @Override
    public void update(Employee item) {
        log.info("Обновление сотрудника: {}", item.getFullName());
        try(Connection connection = connectionToBase.getConnection()) {
            String sql = "UPDATE employees SET full_name = ?, salary = ?, manager_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, item.getFullName());
                statement.setBigDecimal(2, item.getSalary());
                statement.setInt(3, item.getManagerId());
                statement.setInt(4, item.getId());
                statement.executeUpdate();
            }
            log.info("Сотрудник {} успешно обновлен", item.getFullName());
        }catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при обновлении сотрудника: {}", e.getMessage());
        }
    }
    @Override
    public void deleteById(Integer id) {
        log.info("Удаление сотрудника с id: {}", id);
        try (Connection connection = connectionToBase.getConnection()) {
            String sql = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            log.info("Сотрудник с ID {} успешно удален", id);
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при удалении сотрудника: {}", e.getMessage());
        }
    }
}
