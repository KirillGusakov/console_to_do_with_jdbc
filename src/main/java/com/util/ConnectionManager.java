package com.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private static BasicDataSource dataSource;

    // Приватный конструктор, чтобы предотвратить создание экземпляра класса
    private ConnectionManager() {
    }

    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl(PropertiesUtil.getKey(URL_KEY));
        dataSource.setUsername(PropertiesUtil.getKey(USERNAME_KEY));
        dataSource.setPassword(PropertiesUtil.getKey(PASSWORD_KEY));
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(5);
        dataSource.setMaxOpenPreparedStatements(30);
    }

    public static Connection open() throws SQLException {
        return dataSource.getConnection();
    }

}
