package com.example.aplikasikursus.service;

import com.example.aplikasikursus.repository.SiswaRepository;
import com.example.aplikasikursus.domain.Siswa;
import com.example.aplikasikursus.result.Result;

import java.sql.SQLException;
import java.util.List;

public class SiswaService {
    private SiswaRepository siswaRepository;
    public SiswaService(SiswaRepository siswaRepository) {
        this.siswaRepository = siswaRepository;
    }
    public List<Siswa> findAll(String searchQuery) throws SQLException {
        if(searchQuery == null) {
            searchQuery = "";
        }
        return siswaRepository.findAll(searchQuery);
    }
    public List<Siswa> findAllActive() throws SQLException {
        return siswaRepository.findAllActive();
    }

    public Result<Siswa> findById(int id) {
        try {
            Siswa siswa = siswaRepository.findById(id);
            if (siswa != null) {
                return new Result<>(siswa, false, "Siswa found");
            } else {
                return new Result<>(null, true, "Siswa not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Result<>(null, true, "Error retrieving siswa");
        }
    }


    public void updateById(int id, Siswa updatedSiswa) throws SQLException {
        siswaRepository.updateById(id, updatedSiswa);
    }
    public int countSiswaAktif() throws SQLException {
        return siswaRepository.countSiswaAktif();
    }

    public int countAllSiswa() throws SQLException {
        return siswaRepository.countAllSiswa();
    }
}
