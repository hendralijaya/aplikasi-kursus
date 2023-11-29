package com.example.aplikasikursus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DB {
    private static final String url = "jdbc:mysql://localhost:3306/aplikasi-kursus";
    private static final String username = "root";
    private static final String password = "";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
