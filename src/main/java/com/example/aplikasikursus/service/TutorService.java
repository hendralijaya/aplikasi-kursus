package com.example.aplikasikursus.service;

import com.example.aplikasikursus.domain.Tutor;
import com.example.aplikasikursus.repository.TutorRepository;
import com.example.aplikasikursus.result.Result;

import java.sql.SQLException;
import java.util.List;

public class TutorService {
    private TutorRepository tutorRepository;
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }
    public List<Tutor> findAll(String searchQuery) throws SQLException {
        if(searchQuery == null) {
            searchQuery = "";
        }
        return tutorRepository.findAll(searchQuery);
    }

    public List<Tutor> findAllActive() throws SQLException {
        return tutorRepository.findAllActive();
    }

    public int countTutorAktif() throws SQLException {
        return tutorRepository.countTutorAktif();
    }

    public int countAllTutor() throws SQLException {
        return tutorRepository.countAllTutor();
    }
    public Result<Tutor> findById(int id) {
        try {
            Tutor tutor = tutorRepository.findById(id);
            if (tutor != null) {
                return new Result<>(tutor, false, "Tutor found");
            } else {
                return new Result<>(null, true, "Tutor not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Result<>(null, true, "Error retrieving tutor");
        }
    }
}
