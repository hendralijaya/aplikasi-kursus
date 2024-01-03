package com.example.aplikasikursus.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Tutor {
    private int id;
    private String nama;
    private String email;
    private String nomorTelepon;
    private String nomorWA;
    private String gender;
    private String tingkatTutor;
    private String provinsi;
    private String kota;
    private String kodePos;
    private String alamatLengkap;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deactivateAt;

    public Tutor(String nama, String email, String nomorTelepon, String nomorWA, String gender,
                 String tingkatTutor, String provinsi, String kota, String kodePos, String alamatLengkap,
                 String status, Timestamp createdAt, Timestamp updatedAt, Timestamp deactivateAt) {
        this.nama = nama;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.nomorWA = nomorWA;
        this.gender = gender;
        this.tingkatTutor = tingkatTutor;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kodePos = kodePos;
        this.alamatLengkap = alamatLengkap;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deactivateAt = deactivateAt;
    }

    public Tutor(int id, String nama, String email, String nomorTelepon, String nomorWA, String gender,
                 String tingkatTutor, String provinsi, String kota, String kodePos, String alamatLengkap,
                 String status, Timestamp createdAt, Timestamp updatedAt, Timestamp deactivateAt) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.nomorWA = nomorWA;
        this.gender = gender;
        this.tingkatTutor = tingkatTutor;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kodePos = kodePos;
        this.alamatLengkap = alamatLengkap;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deactivateAt = deactivateAt;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getNomorWA() {
        return nomorWA;
    }

    public void setNomorWA(String nomorWA) {
        this.nomorWA = nomorWA;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTingkatTutor() {
        return tingkatTutor;
    }

    public void setTingkatTutor(String tingkatTutor) {
        this.tingkatTutor = tingkatTutor;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
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

    public static Tutor mapResultSetToTutor(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String nama = resultSet.getString("nama");
        String email = resultSet.getString("email");
        String nomorTelepon = resultSet.getString("nomor_telepon");
        String nomorWA = resultSet.getString("nomor_wa");
        String gender = resultSet.getString("gender");
        String tingkatTutor = resultSet.getString("tingkat_tutor");
        String provinsi = resultSet.getString("provinsi");
        String kota = resultSet.getString("kota");
        String kodePos = resultSet.getString("kode_pos");
        String alamatLengkap = resultSet.getString("alamat_lengkap");
        String status = resultSet.getString("status");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        Timestamp deactivateAt = resultSet.getTimestamp("deactivate_at");

        // Handle null values
        if (resultSet.wasNull()) {
            // Set the corresponding fields to null
            createdAt = null;
            updatedAt = null;
            deactivateAt = null;
        }

        return new Tutor(id, nama, email, nomorTelepon, nomorWA, gender, tingkatTutor, provinsi, kota, kodePos,
                alamatLengkap, status, createdAt, updatedAt, deactivateAt);
    }
}
