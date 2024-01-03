package com.example.aplikasikursus.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String url = "jdbc:mysql://localhost:3306/aplikasi-kursus";
    private static final String username = "root";
    private static final String password = "";
    private static Connection connection;

    private DB() {
        // Private constructor to enforce singleton pattern
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
