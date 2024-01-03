package com.example.aplikasikursus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class AboutController {
    @FXML
    private Button homeButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    public void initialize() {
        // Set the home button as active when the application starts
        setActiveButton(aboutButton);
    }

    @FXML
    private void handleHomeButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/landingpage/home.fxml");
    }

    @FXML
    private void handleAboutButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/landingpage/about.fxml");
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/landingpage/login.fxml");
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/landingpage/register.fxml");
    }
    private void setActiveButton(Button button) {
        // Remove active class from all buttons
        homeButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        loginButton.getStyleClass().remove("active");
        registerButton.getStyleClass().remove("active");

        // Add active class to the specified button
        button.getStyleClass().add("active");
    }
    private void loadPage(String fxmlFile) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the scene from any of the buttons
            Scene scene = homeButton.getScene();

            // Set the new root for the scene
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during loading
        }
    }
}
