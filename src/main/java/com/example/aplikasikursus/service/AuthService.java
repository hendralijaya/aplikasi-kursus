package com.example.aplikasikursus.service;

import com.example.aplikasikursus.domain.Akun;
import com.example.aplikasikursus.repository.AkunRepository;
import com.example.aplikasikursus.result.Result;
import com.example.aplikasikursus.utils.Hash;

import java.sql.SQLException;

public class AuthService {
    private AkunRepository akunRepository;

    public AuthService(AkunRepository akunRepository) {
        this.akunRepository = akunRepository;
    }

    public Result<Akun> register(String username, String passwordInput, String repasswordInput, String role) {
        if (username.isEmpty() || passwordInput.isEmpty() || repasswordInput.isEmpty() || role.isEmpty()) {
            return new Result<>(null, true, "All fields must be filled.");
        }

        if (!passwordInput.equals(repasswordInput)) {
            return new Result<>(null, true, "Passwords do not match.");
        }

        // Proceed with registration
        Akun akun = new Akun(username, passwordInput, role);
        try {
            akunRepository.insertOneAdmin(akun);
            return new Result<>(akun, false, "Registration successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Result<>(null, true, "Registration failed. Please try again.");
        }
    }

    public Result<Akun> login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new Result<>(null, true, "Username and password are required.");
        }

        // Perform the login logic
        try {
            // Retrieve the user from the database based on the provided username
            Akun akun = akunRepository.findByUsername(username);

            if (akun == null) {
                return new Result<>(null, true, "Invalid username or password.");
            }

            // Check if the provided password matches the stored password
            String hashedPassword = Hash.hash(password);
            if (!akun.getPassword().equals(hashedPassword)) {
                return new Result<>(null, true, "Invalid username or password.");
            }

            // Login successful
            return new Result<>(akun, false, "Login successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Result<>(null, true, "Login failed. Please try again.");
        }
    }
}

