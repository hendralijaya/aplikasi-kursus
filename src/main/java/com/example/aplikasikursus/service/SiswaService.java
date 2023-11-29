package com.example.aplikasikursus;

import com.example.aplikasikursus.domain.Siswa;

import java.sql.SQLException;
import java.util.List;

public class SiswaService {
    private SiswaRepository siswaRepository;
    public SiswaService(SiswaRepository siswaRepository) {
        this.siswaRepository = siswaRepository;
    }
    public List<SiswaRepository> findAll() throws SQLException {
        return siswaRepository.findAll();
    }

    public SiswaRepository findById(int id) throws SQLException {
        return siswaRepository.findById(id);
    }

    public void updateById(int id, Siswa updatedSiswa) throws SQLException {
        siswaRepository.updateById(id, updatedSiswa);
    }
}
