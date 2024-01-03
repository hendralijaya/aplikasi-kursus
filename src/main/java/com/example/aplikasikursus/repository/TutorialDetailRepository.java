package com.example.aplikasikursus.repository;

import com.example.aplikasikursus.config.DB;
import com.example.aplikasikursus.domain.TutorialDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorialDetailRepository {
    private Connection connection;
    public TutorialDetailRepository () {
        try {
            this.connection = DB.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<TutorialDetail> findByTutorialId(int tutorialId) {
        List<TutorialDetail> tutorialDetailList = new ArrayList<>();
        String query = "SELECT * FROM tutorial_detail WHERE tutorial_id = ? ORDER BY pertemuan ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorialId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TutorialDetail tutorialDetail = TutorialDetail.mapResultSetToTutorialDetail(resultSet);
                tutorialDetailList.add(tutorialDetail);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tutorialDetailList;
    }
    public int countPertemuanByTutorialId(int tutorialId) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM tutorial_detail WHERE tutorial_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorialId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public void insertOne(TutorialDetail tutorialDetail) {
        String query = "INSERT INTO tutorial_detail (tutorial_id, sesi, pertemuan, durasi, tanggal_tutorial, jam_mulai, jam_selesai, realisasi_materi, realisasi_pr, rating_tutor, rating_siswa, nilai, diinput_oleh, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorialDetail.getTutorialId());
            statement.setString(2, tutorialDetail.getSesi());
            statement.setInt(3, tutorialDetail.getPertemuan());
            statement.setInt(4, tutorialDetail.getDurasi());
            java.util.Date utilDate = tutorialDetail.getTanggalTutorial();
            java.sql.Date tanggalTutorial = new java.sql.Date(utilDate.getTime());
            statement.setDate(5, tanggalTutorial);
            statement.setTime(6, tutorialDetail.getJamMulai());
            statement.setTime(7, tutorialDetail.getJamSelesai());
            statement.setString(8, tutorialDetail.getRealisasiMateri());
            statement.setString(9, tutorialDetail.getRealisasiPr());
            statement.setInt(10, tutorialDetail.getRatingTutor());
            statement.setInt(11, tutorialDetail.getRatingSiswa());
            statement.setFloat(12, tutorialDetail.getNilai());
            statement.setString(13, tutorialDetail.getDiinputOleh());
            statement.setString(14, tutorialDetail.getStatus());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void deactivateByTutorialId(int tutorialId) {
        String query = "UPDATE tutorial_detail SET status = 'D' WHERE tutorial_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tutorialId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
