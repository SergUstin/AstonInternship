package com.campany.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConnectionToBase extends ConnectionToBase {
    private final String dbUrl;
    private final String user;
    private final String password;

    public TestConnectionToBase(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    @Override
    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        log.info("Установка соединения с тестовой базой данных");
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(dbUrl, user, password);
    }
}

