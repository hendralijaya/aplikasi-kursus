package com.example.aplikasikursus.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date; // Import java.util.Date
public class Tutorial {
    private int id;
    private int siswaId;
    private int tutorId;
    private int coursesId;
    private int term;
    private Date tanggalMulaiTerm;
    private double nilaiAkhir;
    private int kehadiran;
    private Time jamKehadiran;
    private String catatanTutor;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deactivateAt;

    public Tutorial() {
    }

    public Tutorial(int id, int siswaId, int tutorId, int coursesId, int term, Date tanggalMulaiTerm,
                    double nilaiAkhir, int kehadiran, Time jamKehadiran, String catatanTutor, String status) {
        this.id = id;
        this.siswaId = siswaId;
        this.tutorId = tutorId;
        this.coursesId = coursesId;
        this.term = term;
        this.tanggalMulaiTerm = tanggalMulaiTerm;
        this.nilaiAkhir = nilaiAkhir;
        this.kehadiran = kehadiran;
        this.jamKehadiran = jamKehadiran;
        this.catatanTutor = catatanTutor;
        this.status = status;
    }

    public Tutorial(int siswaId, int tutorId, int coursesId, int term, Date tanggalMulaiTerm,
                    double nilaiAkhir, int kehadiran, Time jamKehadiran, String catatanTutor, String status) {
        this.siswaId = siswaId;
        this.tutorId = tutorId;
        this.coursesId = coursesId;
        this.term = term;
        this.tanggalMulaiTerm = tanggalMulaiTerm;
        this.nilaiAkhir = nilaiAkhir;
        this.kehadiran = kehadiran;
        this.jamKehadiran = jamKehadiran;
        this.catatanTutor = catatanTutor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiswaId() {
        return siswaId;
    }

    public void setSiswaId(int siswaId) {
        this.siswaId = siswaId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(int coursesId) {
        this.coursesId = coursesId;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Date getTanggalMulaiTerm() {
        return tanggalMulaiTerm;
    }

    public void setTanggalMulaiTerm(Date tanggalMulaiTerm) {
        this.tanggalMulaiTerm = tanggalMulaiTerm;
    }

    public double getNilaiAkhir() {
        return nilaiAkhir;
    }

    public void setNilaiAkhir(double nilaiAkhir) {
        this.nilaiAkhir = nilaiAkhir;
    }

    public int getKehadiran() {
        return kehadiran;
    }

    public void setKehadiran(int kehadiran) {
        this.kehadiran = kehadiran;
    }

    public Time getJamKehadiran() {
        return jamKehadiran;
    }

    public void setJamKehadiran(Time jamKehadiran) {
        this.jamKehadiran = jamKehadiran;
    }

    public String getCatatanTutor() {
        return catatanTutor;
    }

    public void setCatatanTutor(String catatanTutor) {
        this.catatanTutor = catatanTutor;
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
    public static Tutorial mapResultSetToTutorial(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int idSiswa = resultSet.getInt("siswa_id");
        int idTutor = resultSet.getInt("tutor_id");
        int idCourses = resultSet.getInt("courses_id");
        int term = resultSet.getInt("term");
        java.sql.Date sqlTanggalMulaiTerm = resultSet.getDate("tanggal_mulai_term");
        java.util.Date tanggalMulaiTerm = new java.util.Date(sqlTanggalMulaiTerm.getTime());
        double nilaiAkhir = resultSet.getDouble("nilai_akhir");
        int kehadiran = resultSet.getInt("kehadiran");
        Time jamKehadiran = resultSet.getTime("jam_kehadiran");
        String catatanTutor = resultSet.getString("catatan_tutor");
        String status = resultSet.getString("status");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        Timestamp deactivateAt = resultSet.getTimestamp("deactivate_at");

        // Handle null values
        if (resultSet.getObject("updated_at") == null) {
            updatedAt = null;
        }

        if (resultSet.getObject("deactivate_at") == null) {
            deactivateAt = null;
        }

        Tutorial tutorial = new Tutorial(id, idSiswa, idTutor, idCourses, term, tanggalMulaiTerm,
                nilaiAkhir, kehadiran, jamKehadiran, catatanTutor, status);
        tutorial.setCreatedAt(createdAt);
        tutorial.setUpdatedAt(updatedAt);
        tutorial.setDeactivateAt(deactivateAt);
        return tutorial;
    }
}
