package com.airlines.dao;

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

    public Connection getDBConnection() {

        Connection dbConnection = null;
        try {
            dbConnection = datasource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}

