package com.example.aplikasikursus.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class TutorialDetail {
    private int id;
    private int tutorialId;
    private String sesi;
    private int pertemuan;
    private int durasi;
    private Date tanggalTutorial;
    private Time jamMulai;
    private Time jamSelesai;
    private String realisasiMateri;
    private String realisasiPr;
    private int ratingTutor;
    private int ratingSiswa;
    private float nilai;
    private String diinputOleh;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deactivateAt;

    public TutorialDetail(int id, int tutorialId, String sesi, int pertemuan, int durasi,
                          Date tanggalTutorial, Time jamMulai, Time jamSelesai, String realisasiMateri,
                          String realisasiPr, int ratingTutor, int ratingSiswa, float nilai, String diinputOleh,
                          String status) {
        this.id = id;
        this.tutorialId = tutorialId;
        this.sesi = sesi;
        this.pertemuan = pertemuan;
        this.durasi = durasi;
        this.tanggalTutorial = tanggalTutorial;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.realisasiMateri = realisasiMateri;
        this.realisasiPr = realisasiPr;
        this.ratingTutor = ratingTutor;
        this.ratingSiswa = ratingSiswa;
        this.nilai = nilai;
        this.diinputOleh = diinputOleh;
        this.status = status;
    }
    public TutorialDetail(int tutorialId, String sesi, int pertemuan, int durasi,
                          Date tanggalTutorial, Time jamMulai, Time jamSelesai, String realisasiMateri,
                          String realisasiPr, int ratingTutor, int ratingSiswa, float nilai, String diinputOleh,
                          String status) {
        this.tutorialId = tutorialId;
        this.sesi = sesi;
        this.pertemuan = pertemuan;
        this.durasi = durasi;
        this.tanggalTutorial = tanggalTutorial;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.realisasiMateri = realisasiMateri;
        this.realisasiPr = realisasiPr;
        this.ratingTutor = ratingTutor;
        this.ratingSiswa = ratingSiswa;
        this.nilai = nilai;
        this.diinputOleh = diinputOleh;
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(int tutorialId) {
        this.tutorialId = tutorialId;
    }

    public String getSesi() {
        return sesi;
    }

    public void setSesi(String sesi) {
        this.sesi = sesi;
    }

    public int getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(int pertemuan) {
        this.pertemuan = pertemuan;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public Date getTanggalTutorial() {
        return tanggalTutorial;
    }

    public void setTanggalTutorial(Date tanggalTutorial) {
        this.tanggalTutorial = tanggalTutorial;
    }

    public Time getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(Time jamMulai) {
        this.jamMulai = jamMulai;
    }

    public Time getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(Time jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getRealisasiMateri() {
        return realisasiMateri;
    }

    public void setRealisasiMateri(String realisasiMateri) {
        this.realisasiMateri = realisasiMateri;
    }

    public String getRealisasiPr() {
        return realisasiPr;
    }

    public void setRealisasiPr(String realisasiPr) {
        this.realisasiPr = realisasiPr;
    }

    public int getRatingTutor() {
        return ratingTutor;
    }

    public void setRatingTutor(int ratingTutor) {
        this.ratingTutor = ratingTutor;
    }

    public int getRatingSiswa() {
        return ratingSiswa;
    }

    public void setRatingSiswa(int ratingSiswa) {
        this.ratingSiswa = ratingSiswa;
    }

    public float getNilai() {
        return nilai;
    }

    public void setNilai(float nilai) {
        this.nilai = nilai;
    }

    public String getDiinputOleh() {
        return diinputOleh;
    }

    public void setDiinputOleh(String diinputOleh) {
        this.diinputOleh = diinputOleh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeactivateAt() {
        return deactivateAt;
    }

    public void setDeactivateAt(Timestamp deactivateAt) {
        this.deactivateAt = deactivateAt;
    }
    public static TutorialDetail mapResultSetToTutorialDetail(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int tutorialId = resultSet.getInt("tutorial_id");
        String sesi = resultSet.getString("sesi");
        int pertemuan = resultSet.getInt("pertemuan");
        int durasi = resultSet.getInt("durasi");
        Date tanggalTutorial = resultSet.getDate("tanggal_tutorial");
        Time jamMulai = resultSet.getTime("jam_mulai");
        Time jamSelesai = resultSet.getTime("jam_selesai");
        String realisasiMateri = resultSet.getString("realisasi_materi");
        String realisasiPr = resultSet.getString("realisasi_pr");
        int ratingTutor = resultSet.getInt("rating_tutor");
        int ratingSiswa = resultSet.getInt("rating_siswa");
        float nilai = resultSet.getFloat("nilai");
        String diinputOleh = resultSet.getString("diinput_oleh");
        String status = resultSet.getString("status");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        Timestamp deactivateAt = resultSet.getTimestamp("deactivate_at");
        if (resultSet.getObject("updated_at") == null) {
            updatedAt = null;
        }

        if (resultSet.getObject("deactivate_at") == null) {
            deactivateAt = null;
        }

        TutorialDetail tutorialDetail = new TutorialDetail(id, tutorialId, sesi, pertemuan, durasi, tanggalTutorial, jamMulai,
                jamSelesai, realisasiMateri, realisasiPr, ratingTutor, ratingSiswa, nilai, diinputOleh, status);
        tutorialDetail.setCreatedAt(createdAt);
        tutorialDetail.setUpdatedAt(updatedAt);
        tutorialDetail.setDeactivateAt(deactivateAt);
        return tutorialDetail;
    }
}
