package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.domain.Akun;
import com.example.aplikasikursus.repository.AkunRepository;
import com.example.aplikasikursus.result.Result;
import com.example.aplikasikursus.service.AuthService;
import com.example.aplikasikursus.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoginController {
    private AuthService authService;
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
    public void initialize() {
        // Set the home button as active when the application starts
        setActiveButton(loginButton);
    }
    public LoginController() {
        AkunRepository akunRepository = new AkunRepository(); // Initialize your AkunRepository
        this.authService = new AuthService(akunRepository);
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

            // Get the controller instance from the loader
            Object controller = loader.getController();

            // Check if the controller has an "initialize" method
            try {
                Method initializeMethod = controller.getClass().getMethod("initialize");
                // Invoke the "initialize" method
                initializeMethod.invoke(controller);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                // Handle any errors that occur during method invocation or if the method doesn't exist
                e.printStackTrace();
            }

            // Get the scene from any of the buttons
            Scene scene = homeButton.getScene();

            // Set the new root for the scene
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during loading
        }
    }


    @FXML
    private void handleLoginHandlerButton(ActionEvent event) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username.isEmpty() || password.isEmpty()) {
            // Display an error message if either the username or password is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Please enter both username and password.");
            alert.showAndWait();
            return; // Return without performing the login
        }

        // Perform the login using your authentication service
        Result<Akun> loginResult = authService.login(username, password);

        if (loginResult.isError()) {
            String errorMessage = loginResult.getMessage();
            // Display the error message in an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Login failed");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        } else {
            // Login successful
            Akun akun = loginResult.getObject();

            // Set the session as an admin if the user is an admin
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.setCurrentUser(akun);
            if (akun.isAdmin()) {
                loadPage("/com/example/aplikasikursus/view/dashboardadmin/index.fxml");
            } else {
                loadPage("/com/example/aplikasikursus/dashboardTutor.fxml");
            }
        }
    }

}
