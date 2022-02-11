package com.airlines.dao;

import com.airlines.exception.DaoOperationException;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnector {
    HikariDataSource datasource;

    public DbConnector() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        String url = resourceBundle.getString("url");
        String username = resourceBundle.getString("username");
        String password = resourceBundle.getString("password");

        datasource = new HikariDataSource();
        datasource.setJdbcUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
    }

    public Connection getDbConnection() {
        try {
            return datasource.getConnection();
        } catch (SQLException e) {
            throw new DaoOperationException("Can not obtain connection from datasource");
        }
    }

}

