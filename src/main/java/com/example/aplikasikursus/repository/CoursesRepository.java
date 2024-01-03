package com.example.aplikasikursus.repository;

import com.example.aplikasikursus.config.DB;
import com.example.aplikasikursus.domain.Courses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesRepository {
    private Connection connection;

    public CoursesRepository () {
        try {
            this.connection = DB.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<Courses> findAll(String searchQuery) throws SQLException {
        // Check if searchQuery is null and assign a default value if it is
        if (searchQuery == null) {
            searchQuery = ""; // Set default value to an empty string or any other default search value you prefer
        }

        List<Courses> coursesList = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE CONCAT(nama_kursus, status) LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchQuery + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Courses courses = Courses.mapResultSetToCourses(resultSet);
                    coursesList.add(courses);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    public List<Courses> findAllActive() throws SQLException {
        List<Courses> activeCoursesList = new ArrayList<>();

        String query = "SELECT * FROM courses WHERE status = 'A'";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Courses courses = Courses.mapResultSetToCourses(resultSet);
            activeCoursesList.add(courses);
        }

        resultSet.close();
        statement.close();

        return activeCoursesList;
    }

    public int countCoursesAktif() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM courses WHERE status = 'A'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }

        return 0;
    }

    public int countAllCourses() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM courses";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }

        return 0;
    }
    public void insertOne(Courses courses) throws SQLException {
        String query = "INSERT INTO courses (nama_kursus, harga, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courses.getNamaKursus());
            statement.setDouble(2, courses.getHarga());
            statement.setString(3, courses.getStatus().substring(0,1));
            statement.executeUpdate();
        }
    }

    public void updateById(int id, Courses courses) throws SQLException {
        String query = "UPDATE courses SET nama_kursus = ?, harga = ?, status = ?, updated_at = ?";

        // Check if the status starts with "D" to determine whether to update deactivateAt
        if (courses.getStatus().startsWith("D")) {
            query += ", deactivate_at = ?";
        }

        query += " WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            statement.setString(parameterIndex++, courses.getNamaKursus());
            statement.setDouble(parameterIndex++, courses.getHarga());
            statement.setString(parameterIndex++, courses.getStatus().substring(0,1));
            statement.setTimestamp(parameterIndex++, new Timestamp(System.currentTimeMillis()));

            // Set deactivateAt parameter if status starts with "D"
            if (courses.getStatus().startsWith("D")) {
                statement.setTimestamp(parameterIndex++, new Timestamp(System.currentTimeMillis()));
            }

            statement.setInt(parameterIndex, id);
            statement.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM courses WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public Courses findById(int id) throws SQLException {
        String query = "SELECT * FROM courses WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Courses.mapResultSetToCourses(resultSet);
                }
            }
        }
        return null; // Return null if no course with the given id is found
    }

}
