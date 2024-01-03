package com.example.aplikasikursus.domain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class Courses {
    private int id;
    private String namaKursus;
    private double harga;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deactivateAt;
    // Constructor with id parameter
    public Courses(int id, String namaKursus, double harga, String status) {
        this.id = id;
        this.namaKursus = namaKursus;
        this.harga = harga;
        this.status = status;
    }

    public Courses(String namaKursus, double harga, String status) {
        this.namaKursus = namaKursus;
        this.harga = harga;
        this.status = status;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setNamaKursus(String namaKursus) {
        this.namaKursus =  namaKursus;
    }

    public String getNamaKursus() {
        return namaKursus;
    }
    public void setHarga(double harga) {
        this.harga =  harga;
    }
    public double getHarga() {
        return harga;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeactivateAt(Timestamp deactivateAt) {
        this.deactivateAt = deactivateAt;
    }
    public static Courses mapResultSetToCourses(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String namaKursus = resultSet.getString("nama_kursus");
        double harga = resultSet.getDouble("harga");
        String status = resultSet.getString("status");
        Timestamp createdAt = resultSet.getObject("created_at", Timestamp.class);
        Timestamp updatedAt = resultSet.getObject("updated_at", Timestamp.class);
        Timestamp deactivateAt = resultSet.getObject("deactivate_at", Timestamp.class);

        // Handle null values
        if (resultSet.getObject("updated_at") == null) {
            updatedAt = null;
        }

        if (resultSet.getObject("deactivate_at") == null) {
            deactivateAt = null;
        }
        Courses courses = new Courses(id, namaKursus, harga, status);
        courses.setCreatedAt(createdAt);
        courses.setUpdatedAt(updatedAt);
        courses.setDeactivateAt(deactivateAt);

        return courses;
    }

}
