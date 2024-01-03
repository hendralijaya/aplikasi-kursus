package com.example.aplikasikursus.domain;

public class Akun {
    private int id;
    private String username;
    private String password;
    private String role;
    private String tutorId;
    public Akun(int id, String username, String password, String role, String tutorId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.tutorId = tutorId;
    }

    public Akun(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Akun(String username, String password, String role, String tutorId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.tutorId = tutorId;
    }

    public Akun(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }
    public boolean isAdmin() {
        return "admin".equals(role);
    }

}
