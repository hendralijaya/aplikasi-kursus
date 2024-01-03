package com.example.aplikasikursus.service;

import com.example.aplikasikursus.domain.Tutorial;
import com.example.aplikasikursus.repository.TutorialRepository;
import com.example.aplikasikursus.result.Result;

import java.sql.SQLException;
import java.util.List;

public class TutorialService {
    private TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public List<Tutorial> findAllDeactivate() {
        try {
            return tutorialRepository.findAllDeactivate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately, such as logging or throwing a custom exception
        }
        return null; // or an empty list depending on your error handling strategy
    }

    public List<Tutorial> findAllActive() {
        try {
            return tutorialRepository.findAllActive();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately, such as logging or throwing a custom exception
        }
        return null; // or an empty list depending on your error handling strategy
    }

    public Result<String> insertOne(Tutorial tutorial) {
        try {
            tutorialRepository.insertOne(tutorial);
            return new Result<>("Success", false, "Tutorial berhasil ditambahkan");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal menambahkan tutorial: " + ex.getMessage());
        }
    }

    public int countTermBySiswaId(int siswaId) {
        return tutorialRepository.countTermBySiswaId(siswaId);
    }

    public Result<String> updateById(int id, Tutorial tutorial) {
        try {
            tutorialRepository.updateById(id, tutorial);
            return new Result<>("Success", false, "Tutorial berhasil diperbarui");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal memperbarui tutorial: " + ex.getMessage());
        }
    }
    public Result<String> deleteById(int id) {
        try {
            tutorialRepository.deleteById(id);
            return new Result<>("Success", false, "Tutorial berhasil dihapus");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Result<>(null, true, "Gagal menghapus tutorial: " + ex.getMessage());
        }
    }
}
