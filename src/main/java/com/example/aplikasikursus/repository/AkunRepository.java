package com.example.aplikasikursus.repository;

import com.example.aplikasikursus.config.DB;
import com.example.aplikasikursus.domain.Akun;
import com.example.aplikasikursus.utils.Hash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AkunRepository {
    private Connection connection;

    // Constructor that initializes the database connection
    public AkunRepository() {
        try {
            this.connection = DB.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertOneAdmin(Akun akun) throws SQLException {
        String sql = "INSERT INTO akun (username, password, role) VALUES (?, ?, ?)";
        String hashedPassword = Hash.hash(akun.getPassword());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, akun.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, akun.getRole());
            statement.executeUpdate();
        }
    }

    public Akun findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM akun WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    String tutorId = resultSet.getString("tutor_id");

                    // Create and return the Akun object
                    return new Akun(id, username, password, role, tutorId);
                }
            }
        }

        // No Akun found with the given username
        return null;
    }
}

