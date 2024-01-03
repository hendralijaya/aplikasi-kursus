package com.example.aplikasikursus.domain;

import com.example.aplikasikursus.repository.SiswaRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Siswa extends SiswaRepository {
    private int id;
    private String nama;
    private String namaPanggilan;
    private String email;
    private String nomorTelepon;
    private String nomorWA;
    private String namaSekolahUniversitas;
    private Date tanggalBergabung;
    private String gender;
    private Date tanggalLahir;
    private String provinsi;
    private String kota;
    private String kodePos;
    private String alamatLengkap;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deactivateAt;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNamaPanggilan() {
        return namaPanggilan;
    }

    public String getEmail() {
        return email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public String getNomorWA() {
        return nomorWA;
    }

    public String getNamaSekolahUniversitas() {
        return namaSekolahUniversitas;
    }

    public Date getTanggalBergabung() {
        return tanggalBergabung;
    }

    public String getGender() {
        return gender;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getKota() {
        return kota;
    }

    public String getKodePos() {
        return kodePos;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Timestamp getDeactivateAt() {
        return deactivateAt;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNamaPanggilan(String namaPanggilan) {
        this.namaPanggilan = namaPanggilan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public void setNomorWA(String nomorWA) {
        this.nomorWA = nomorWA;
    }

    public void setNamaSekolahUniversitas(String namaSekolahUniversitas) {
        this.namaSekolahUniversitas = namaSekolahUniversitas;
    }

    public void setTanggalBergabung(Date tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeactivateAt(Timestamp deactivateAt) {
        this.deactivateAt = deactivateAt;
    }
    public Siswa(String nama, String namaPanggilan, String email, String nomorTelepon, String nomorWA, String namaSekolahUniversitas, Date tanggalBergabung, String gender, Date tanggalLahir, String provinsi, String kota, String kodePos, String alamatLengkap, String status, Timestamp createdAt, Timestamp updatedAt, Timestamp deactivateAt) {
        this.nama = nama;
        this.namaPanggilan = namaPanggilan;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.nomorWA = nomorWA;
        this.namaSekolahUniversitas = namaSekolahUniversitas;
        this.tanggalBergabung = tanggalBergabung;
        this.gender = gender;
        this.tanggalLahir = tanggalLahir;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kodePos = kodePos;
        this.alamatLengkap = alamatLengkap;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deactivateAt = deactivateAt;
    }
    public Siswa(int id, String nama, String namaPanggilan, String email, String nomorTelepon, String nomorWA, String namaSekolahUniversitas, Date tanggalBergabung, String gender, Date tanggalLahir, String provinsi, String kota, String kodePos, String alamatLengkap, String status, Timestamp createdAt, Timestamp updatedAt, Timestamp deactivateAt) {
        this.id = id;
        this.nama = nama;
        this.namaPanggilan = namaPanggilan;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.nomorWA = nomorWA;
        this.namaSekolahUniversitas = namaSekolahUniversitas;
        this.tanggalBergabung = tanggalBergabung;
        this.gender = gender;
        this.tanggalLahir = tanggalLahir;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kodePos = kodePos;
        this.alamatLengkap = alamatLengkap;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deactivateAt = deactivateAt;
    }

    public static Siswa mapResultSetToSiswa(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String nama = resultSet.getString("nama");
        String namaPanggilan = resultSet.getString("nama_panggilan");
        String email = resultSet.getString("email");
        String nomorTelepon = resultSet.getString("nomor_telepon");
        String nomorWA = resultSet.getString("nomor_wa");
        String namaSekolahUniversitas = resultSet.getString("nama_sekolah_universitas");
        Date tanggalBergabung = resultSet.getDate("tanggal_bergabung");
        String gender = resultSet.getString("gender");
        Date tanggalLahir = resultSet.getDate("tanggal_lahir");
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
            tanggalBergabung = null;
            tanggalLahir = null;
            createdAt = null;
            updatedAt = null;
            deactivateAt = null;
        }

        return new Siswa(id, nama, namaPanggilan, email, nomorTelepon, nomorWA, namaSekolahUniversitas,
                tanggalBergabung, gender, tanggalLahir, provinsi, kota, kodePos, alamatLengkap,
                status, createdAt, updatedAt, deactivateAt);
    }
}
