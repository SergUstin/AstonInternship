package com.campany.repository;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public abstract class AbstractRepository<T> implements RepositoryMethod<T> {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/aston-test";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        log.info("Установка соединения с базой данных");
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    protected void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                log.info("Соединение с базой данных успешно закрыто");
            }
        } catch (SQLException e) {
            log.error("Ошибка при закрытии соединения с базой данных", e);
        }
    }
}
