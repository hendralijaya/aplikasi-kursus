package com.example.aplikasikursus;
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

    public List<SiswaRepository> findAll() throws SQLException {
        List<SiswaRepository> siswaList = new ArrayList<>();
        String query = "SELECT * FROM siswa";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Siswa siswa = Siswa.mapResultSetToSiswa(resultSet);
                siswaList.add(siswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siswaList;
    }

    public SiswaRepository findById(int id) throws SQLException {
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

    public void updateById(int id, Siswa updatedSiswa) throws SQLException {
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
            statement.executeUpdate();
        }
    }
}
