package com.airlines.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCConnection {

    public static void main(String[] args) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        Connection dbConnection = jdbcConnection.getDBConnection();
    }

    protected Connection getDBConnection() {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        String url = resourceBundle.getString("url");
        String username = resourceBundle.getString("username");
        String password = resourceBundle.getString("password");

        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}

