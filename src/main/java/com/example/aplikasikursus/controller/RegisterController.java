package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.result.Result;
import com.example.aplikasikursus.domain.Akun;
import com.example.aplikasikursus.repository.AkunRepository;
import com.example.aplikasikursus.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Button homeButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField repasswordInput;
    @FXML
    private Button registerHandlerButton;

    private AuthService authService;
    @FXML
    public void initialize() {
        // Set the home button as active when the application starts
        setActiveButton(registerButton);
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

    public RegisterController() {
        AkunRepository akunRepository = new AkunRepository(); // Initialize your AkunRepository
        this.authService = new AuthService(akunRepository);
    }

    @FXML
    private void handleRegisterHandlerButton(ActionEvent event) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String repassword = repasswordInput.getText();

        Result<Akun> registrationResult = authService.register(username, password, repassword, "admin");

        if (registrationResult.isError()) {
            String errorMessage = registrationResult.getMessage();
            // Display the error message in an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText("Registration failed");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        } else {
            Akun akun = registrationResult.getObject();
            // Registration successful, you can now access the registered Akun object
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText("Registration completed");
            alert.setContentText("Registration successful! Username: " + akun.getUsername());
            alert.showAndWait();
            loadPage("/com/example/aplikasikursus/view/landingpage/login.fxml");
        }
    }
}
