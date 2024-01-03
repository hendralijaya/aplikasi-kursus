package com.example.aplikasikursus.service;

import com.example.aplikasikursus.domain.Courses;
import com.example.aplikasikursus.repository.CoursesRepository;
import com.example.aplikasikursus.result.Result;

import java.sql.SQLException;
import java.util.List;

public class CoursesService {
    private CoursesRepository coursesRepository;

    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Courses> findAll(String searchQuery) throws SQLException {
        if (searchQuery == null) {
            searchQuery = "";
        }
        return coursesRepository.findAll(searchQuery);
    }
    public List<Courses> findAllActive() throws SQLException {
        return coursesRepository.findAllActive();
    }
    public int countCoursesAktif() throws SQLException {
        return coursesRepository.countCoursesAktif();
    }

    public int countAllCourses() throws SQLException {
        return coursesRepository.countAllCourses();
    }
    public Result<String> insertOne(Courses courses) {
        try {
            coursesRepository.insertOne(courses);
            return new Result<>("Success", false, "Kursus berhasil ditambahkan");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal menambahkan kursus: " + ex.getMessage());
        }
    }

    public Result<String> updateById(int id, Courses courses) {
        try {
            coursesRepository.updateById(id, courses);
            return new Result<>("Success", false, "Kursus berhasil diperbarui");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal memperbarui kursus: " + ex.getMessage());
        }
    }

    public Result<String> deleteById(int id) {
        try {
            coursesRepository.deleteById(id);
            return new Result<>("Success", false, "Kursus berhasil dihapus");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal menghapus kursus: " + ex.getMessage());
        }
    }

    public Result<Courses> findById(int id) {
        try {
            Courses courses = coursesRepository.findById(id);
            if (courses != null) {
                return new Result<>(courses, false, "Kursus ditemukan");
            } else {
                return new Result<>(null, true, "Kursus tidak ditemukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal mencari kursus: " + ex.getMessage());
        }
    }
}
