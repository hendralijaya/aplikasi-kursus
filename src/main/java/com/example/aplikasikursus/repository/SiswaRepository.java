package com.example.aplikasikursus.repository;
import com.example.aplikasikursus.config.DB;
import com.example.aplikasikursus.domain.Siswa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaRepository {
    private Connection connection;

    public SiswaRepository() {
        try {
            this.connection = DB.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Siswa> findAll(String searchQuery) throws SQLException {
        // Check if searchQuery is null and assign a default value if it is
        if (searchQuery == null) {
            searchQuery = ""; // Set default value to an empty string or any other default search value you prefer
        }

        List<Siswa> siswaList = new ArrayList<>();
        String query = "SELECT * FROM siswa WHERE CONCAT(nama, nama_panggilan, email, nomor_telepon, nomor_wa, nama_sekolah_universitas, provinsi, kota, kode_pos, alamat_lengkap, status) LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchQuery + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Siswa siswa = Siswa.mapResultSetToSiswa(resultSet);
                    siswaList.add(siswa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siswaList;
    }

    public List<Siswa> findAllActive() throws SQLException {
        List<Siswa> activeSiswaList = new ArrayList<>();

        // Assuming you have a database connection established

        // Prepare the SQL statement
        String query = "SELECT * FROM siswa WHERE status = 'A'";
        PreparedStatement statement = connection.prepareStatement(query);

        // Execute the query
        ResultSet resultSet = statement.executeQuery();

        // Iterate over the result set
        while (resultSet.next()) {
            Siswa siswa = Siswa.mapResultSetToSiswa(resultSet);
            // Add the Siswa object to the list
            activeSiswaList.add(siswa);
        }

        // Close the statement and result set
        resultSet.close();
        statement.close();

        // Return the list of active Siswa objects
        return activeSiswaList;
    }


    public Siswa findById(int id) throws SQLException {
        String query = "SELECT * FROM siswa WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Siswa.mapResultSetToSiswa(resultSet);
                }
            }
        }
        return null;
    }

    public String updateById(int id, Siswa updatedSiswa) throws SQLException {
        String query = "UPDATE siswa SET nama=?, nama_panggilan=?, email=?, nomor_telepon=?, nomor_wa=?, nama_sekolah_universitas=?, tanggal_bergabung=?, gender=?, tanggal_lahir=?, provinsi=?, kota=?, kode_pos=?, alamat_lengkap=?, status=?, updated_at=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, updatedSiswa.getNama());
            statement.setString(2, updatedSiswa.getNamaPanggilan());
            statement.setString(3, updatedSiswa.getEmail());
            statement.setString(4, updatedSiswa.getNomorTelepon());
            statement.setString(5, updatedSiswa.getNomorWA());
            statement.setString(6, updatedSiswa.getNamaSekolahUniversitas());
            statement.setDate(7, new java.sql.Date(updatedSiswa.getTanggalBergabung().getTime()));
            statement.setString(8, updatedSiswa.getGender());
            statement.setDate(9, new java.sql.Date(updatedSiswa.getTanggalLahir().getTime()));
            statement.setString(10, updatedSiswa.getProvinsi());
            statement.setString(11, updatedSiswa.getKota());
            statement.setString(12, updatedSiswa.getKodePos());
            statement.setString(13, updatedSiswa.getAlamatLengkap());
            statement.setString(14, updatedSiswa.getStatus());
            statement.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
            statement.setInt(16, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "Siswa successfully updated";
                // or you can use logging framework to log the message
            } else {
                return "Error occured";
                // or you can handle the failure scenario accordingly
            }
        }
    }

    public String insertOne(Siswa newSiswa) throws SQLException {
        String query = "INSERT INTO siswa (nama, nama_panggilan, email, nomor_telepon, nomor_wa, nama_sekolah_universitas, tanggal_bergabung, gender, tanggal_lahir, provinsi, kota, kode_pos, alamat_lengkap, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newSiswa.getNama());
            statement.setString(2, newSiswa.getNamaPanggilan());
            statement.setString(3, newSiswa.getEmail());
            statement.setString(4, newSiswa.getNomorTelepon());
            statement.setString(5, newSiswa.getNomorWA());
            statement.setString(6, newSiswa.getNamaSekolahUniversitas());
            statement.setDate(7, new java.sql.Date(newSiswa.getTanggalBergabung().getTime()));
            statement.setString(8, newSiswa.getGender());
            statement.setDate(9, new java.sql.Date(newSiswa.getTanggalLahir().getTime()));
            statement.setString(10, newSiswa.getProvinsi());
            statement.setString(11, newSiswa.getKota());
            statement.setString(12, newSiswa.getKodePos());
            statement.setString(13, newSiswa.getAlamatLengkap());
            statement.setString(14, newSiswa.getStatus());
            statement.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "Siswa successfully added";
                // or you can use logging framework to log the message
            } else {
                return "Error occured";
                // or you can handle the failure scenario accordingly
            }
        }
    }
    public int countSiswaAktif() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM siswa WHERE status = 'A'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }

    public int countAllSiswa() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM siswa";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }
}
