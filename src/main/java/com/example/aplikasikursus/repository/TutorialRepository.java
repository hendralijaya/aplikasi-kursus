package com.example.aplikasikursus.repository;

import com.example.aplikasikursus.config.DB;
import com.example.aplikasikursus.domain.Tutorial;
import com.example.aplikasikursus.result.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorialRepository {
    private Connection connection;
    public TutorialRepository () {
        try {
            this.connection = DB.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<Tutorial> findAllDeactivate() throws SQLException {
        List<Tutorial> tutorialList = new ArrayList<>();
        String query = "SELECT * FROM tutorial WHERE status = 'D'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Tutorial tutorial = Tutorial.mapResultSetToTutorial(resultSet);
                tutorialList.add(tutorial);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tutorialList;
    }

    public List<Tutorial> findAllActive() throws SQLException {
        List<Tutorial> tutorialList = new ArrayList<>();
        String query = "SELECT * FROM tutorial WHERE status != 'D'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Tutorial tutorial = Tutorial.mapResultSetToTutorial(resultSet);
                tutorialList.add(tutorial);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tutorialList;
    }
    public Result<String> insertOne(Tutorial tutorial) {
        String query = "INSERT INTO tutorial (siswa_id, tutor_id, courses_id, tanggal_mulai_term, status, term) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorial.getSiswaId());
            statement.setInt(2, tutorial.getTutorId());
            statement.setInt(3, tutorial.getCoursesId());
            java.util.Date utilDate = tutorial.getTanggalMulaiTerm();
            java.sql.Date sqlDateTanggalMulaiTerm = new java.sql.Date(utilDate.getTime());
            statement.setDate(4, sqlDateTanggalMulaiTerm);
            statement.setString(5, "A");
            statement.setInt(6, tutorial.getTerm());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return new Result<>(null, false, "Tutorial inserted successfully.");
            } else {
                return new Result<>(null, true, "Failed to insert tutorial.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "An error occurred while inserting tutorial.");
        }
    }
    public int countTermBySiswaId(int siswaId) {
        String query = "SELECT COUNT(DISTINCT term) FROM tutorial WHERE siswa_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, siswaId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0; // Return 0 if an error occurs or no term count found
    }
    public void updateById(int id, Tutorial tutorial) throws SQLException {
        String query = "UPDATE tutorial SET siswa_id = ?, tutor_id = ?, courses_id = ?, tanggal_mulai_term = ?, status = ?, term = ?, nilai_akhir = ?, kehadiran = ?, jam_kehadiran = ?, catatan_tutor = ?, status = ?, updated_at = ?, deactivate_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorial.getSiswaId());
            statement.setInt(2, tutorial.getTutorId());
            statement.setInt(3, tutorial.getCoursesId());
            java.util.Date utilDate = tutorial.getTanggalMulaiTerm();
            java.sql.Date sqlDateTanggalMulaiTerm = new java.sql.Date(utilDate.getTime());
            statement.setDate(4, sqlDateTanggalMulaiTerm);
            statement.setString(5, tutorial.getStatus());
            statement.setInt(6, tutorial.getTerm());
            statement.setDouble(7, tutorial.getNilaiAkhir());
            statement.setInt(8, tutorial.getKehadiran());
            statement.setTime(9, tutorial.getJamKehadiran());
            statement.setString(10, tutorial.getCatatanTutor());
            statement.setString(11, tutorial.getStatus());
            java.util.Date utilDateUpdatedAt = tutorial.getUpdatedAt();
            java.sql.Timestamp sqlTimestampUpdatedAt = new java.sql.Timestamp(utilDateUpdatedAt.getTime());
            statement.setTimestamp(12, sqlTimestampUpdatedAt);

            if ("D".equals(tutorial.getStatus())) {
                java.util.Date utilDateDeactivateAt = new java.util.Date();
                java.sql.Timestamp sqlTimestampDeactivateAt = new java.sql.Timestamp(utilDateDeactivateAt.getTime());
                statement.setTimestamp(13, sqlTimestampDeactivateAt);
            } else {
                statement.setTimestamp(13, null); // or statement.setNull(13, Types.TIMESTAMP);
            }

            statement.setInt(14, id);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM tutorial WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
