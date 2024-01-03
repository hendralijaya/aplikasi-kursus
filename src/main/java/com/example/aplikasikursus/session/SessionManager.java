package com.example.aplikasikursus.session;

import com.example.aplikasikursus.domain.Akun;

public class SessionManager {
    private static SessionManager instance;
    private Akun currentUser;

    private SessionManager() {
        // Private constructor to enforce singleton pattern
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Akun getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Akun currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
}
