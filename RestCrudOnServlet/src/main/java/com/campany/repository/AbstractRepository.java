package com.campany.repository;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Абстрактный класс, предоставляющий реализацию некоторых методов для взаимодействия с репозиторием.
 *
 * @param <T> Тип сущности, с которой работает репозиторий
 */
@Slf4j
public abstract class AbstractRepository<T> implements RepositoryMethod<T> {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/aston-test";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    /**
     * Получает соединение с базой данных.
     *
     * @return Объект Connection, представляющий соединение с базой данных
     * @throws SQLException           если возникает ошибка доступа к базе данных
     * @throws ClassNotFoundException если класс драйвера базы данных не найден
     */
    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        log.info("Установка соединения с базой данных");
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * Закрывает соединение с базой данных.
     *
     * @param connection Соединение, которое требуется закрыть
     */
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
