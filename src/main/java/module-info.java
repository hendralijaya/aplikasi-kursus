module com.example.aplikasikursus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.aplikasikursus to javafx.fxml;
    exports com.example.aplikasikursus;
    exports com.example.aplikasikursus.service;
    opens com.example.aplikasikursus.service to javafx.fxml;
    exports com.example.aplikasikursus.repository;
    opens com.example.aplikasikursus.repository to javafx.fxml;
    exports com.example.aplikasikursus.controller;
    opens com.example.aplikasikursus.controller to javafx.fxml;
    exports com.example.aplikasikursus.config;
    opens com.example.aplikasikursus.config to javafx.fxml;
    opens com.example.aplikasikursus.domain to javafx.base;
}