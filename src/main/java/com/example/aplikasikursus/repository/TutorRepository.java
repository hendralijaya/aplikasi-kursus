package com.example.aplikasikursus.repository;

import com.example.aplikasikursus.config.DB;
import com.example.aplikasikursus.domain.Tutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorRepository {
    private Connection connection;

    public TutorRepository () {
        try {
            this.connection = DB.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<Tutor> findAll(String searchQuery) throws SQLException {
        // Check if searchQuery is null and assign a default value if it is
        if (searchQuery == null) {
            searchQuery = ""; // Set default value to an empty string or any other default search value you prefer
        }

        List<Tutor> tutorList = new ArrayList<>();
        String query = "SELECT * FROM tutor WHERE CONCAT(nama, email, nomor_telepon, nomor_wa, gender, tingkat_tutor, provinsi, kota, kode_pos, alamat_lengkap, status) LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchQuery + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tutor tutor = Tutor.mapResultSetToTutor(resultSet);
                    tutorList.add(tutor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutorList;
    }

    public List<Tutor> findAllActive() throws SQLException {
        List<Tutor> activeTutorList = new ArrayList<>();

        // Prepare the SQL statement
        String query = "SELECT * FROM tutor WHERE status = 'A'";
        PreparedStatement statement = connection.prepareStatement(query);

        // Execute the query
        ResultSet resultSet = statement.executeQuery();

        // Iterate over the result set
        while (resultSet.next()) {
            Tutor tutor = Tutor.mapResultSetToTutor(resultSet);
            // Add the Tutor object to the list
            activeTutorList.add(tutor);
        }

        // Close the statement and result set
        resultSet.close();
        statement.close();

        // Return the list of active Tutor objects
        return activeTutorList;
    }

    public int countTutorAktif() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM tutor WHERE status = 'A'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }

    public int countAllTutor() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM tutor";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }
    public Tutor findById(int id) throws SQLException {
        String query = "SELECT * FROM tutor WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Tutor.mapResultSetToTutor(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
