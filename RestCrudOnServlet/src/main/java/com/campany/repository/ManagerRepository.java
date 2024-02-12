package com.campany.repository;

import com.campany.entity.Manager;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ManagerRepository extends AbstractRepository<Manager> {
    @Override
    public Manager findById(Integer id) {
        log.info("Поиск менеджера по ID: {}", id);
        Manager manager = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM managers WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        manager = new Manager(resultSet.getInt("id"), resultSet.getString("full_name"),
                                resultSet.getBigDecimal("salary"));
                        log.info("Найден менеджер с ID {}: {}", id, manager);
                    } else {
                        log.info("Менеджер с ID {} не найден", id);
                    }
                }
            }
        }catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при выполнении поиска менеджера по ID {}", id, e);
        }
        return manager;
    }
    @Override
    public List<Manager> findAll() {
        log.info("Получение всех менеджеров");
        List<Manager> managers = new ArrayList<>();
        try(Connection connection = getConnection()) {
            String sql = "SELECT * FROM managers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Manager manager = new Manager(resultSet.getInt("id"),
                            resultSet.getString("full_name"),
                            resultSet.getBigDecimal("salary"));
                    managers.add(manager);
                }
            }
            log.info("Получено {} менеджеров", managers.size());
        }catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при получении менеджеров", e);
        }
        return managers;
    }
    @Override
    public void save(Manager item) {
        log.info("Сохранение менеджера: {}", item.getFullName());
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.setString(2, item.getFullName());
                preparedStatement.setBigDecimal(3, item.getSalary());
                preparedStatement.executeUpdate();
            }
            log.info("Менеджер {} успешно сохранен", item.getFullName());
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при сохранении менеджера", e);
        }
    }
    @Override
    public void update(Manager item) {
        log.info("Обновление менеджера: {}", item.getFullName());
        try (Connection connection = getConnection()) {
            String sql = "UPDATE managers SET full_name = ?, salary = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, item.getFullName());
                statement.setBigDecimal(2, item.getSalary());
                statement.setInt(3, item.getId());
                statement.executeUpdate();
            }
            log.info("Менеджер {} успешно обновлен", item.getFullName());
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при обновлении менеджера: {}", e.getMessage());
        }
    }
    @Override
    public void deleteById(Integer id) {
        log.info("Удаление менеджера с id: {}", id);
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM managers WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            log.info("Менеджер с ID {} успешно удален", id);
        }catch (SQLException | ClassNotFoundException e) {
            log.error("Ошибка при удалении менеджера: {}", e.getMessage());
        }
    }
}
