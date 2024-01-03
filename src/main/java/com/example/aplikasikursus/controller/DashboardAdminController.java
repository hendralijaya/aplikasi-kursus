package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.domain.Akun;
import com.example.aplikasikursus.repository.CoursesRepository;
import com.example.aplikasikursus.repository.SiswaRepository;
import com.example.aplikasikursus.repository.TutorRepository;
import com.example.aplikasikursus.service.CoursesService;
import com.example.aplikasikursus.service.SiswaService;
import com.example.aplikasikursus.service.TutorService;
import com.example.aplikasikursus.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class DashboardAdminController {
    @FXML
    private Button dashboardButton;
    @FXML
    private Button dataSiswaButton;
    @FXML
    private Button dataTutorButton;
    @FXML
    private Button dataKursusButton;
    @FXML
    private Button dataTutorialButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label jumlahSiswaLabel;
    @FXML
    private Label jumlahSiswaAktifLabel;
    @FXML
    private Label jumlahTutorLabel;
    @FXML
    private Label jumlahTutorAktifLabel;
    @FXML
    private Label jumlahCourseLabel;
    @FXML
    private Label jumlahCourseAktifLabel;
    private CoursesService coursesService;
    private SiswaService siswaService;
    private TutorService tutorService;
    @FXML
    private void handleDashboardButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/dashboardadmin/index.fxml");
    }

    @FXML
    private void handleDataSiswaButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/dashboardadmin/siswa.fxml");
    }

    @FXML
    private void handleDataTutorButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/dashboardadmin/tutor.fxml");
    }

    @FXML
    private void handleDataKursusButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/dashboardadmin/kursus.fxml");
    }

    @FXML
    private void handleDataTutorialButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/dashboardadmin/tutorial.fxml");
    }
    @FXML
    private void handleLogoutButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/landingpage/login.fxml");
    }

    public DashboardAdminController() {
        CoursesRepository coursesRepository = new CoursesRepository();
        coursesService = new CoursesService(coursesRepository);
        SiswaRepository siswaRepository = new SiswaRepository();
        siswaService = new SiswaService(siswaRepository);
        TutorRepository tutorRepository = new TutorRepository();
        tutorService = new TutorService(tutorRepository);
    }

    @FXML
    public void initialize() {
        try {
            setActiveButton(dashboardButton);
            int jumlahSiswa = siswaService.countAllSiswa();
            int jumlahSiswaAktif = siswaService.countSiswaAktif();
            int jumlahTutor = tutorService.countAllTutor();
            int jumlahTutorAktif = tutorService.countTutorAktif();
            int jumlahCourses = coursesService.countAllCourses();
            int jumlahCoursesAktif = coursesService.countCoursesAktif();

            jumlahSiswaLabel.setText(Integer.toString(jumlahSiswa));
            jumlahSiswaAktifLabel.setText(Integer.toString(jumlahSiswaAktif));
            jumlahTutorLabel.setText(Integer.toString(jumlahTutor));
            jumlahTutorAktifLabel.setText(Integer.toString(jumlahTutorAktif));
            jumlahCourseLabel.setText(Integer.toString(jumlahCourses));
            jumlahCourseAktifLabel.setText(Integer.toString(jumlahCoursesAktif));
            Akun currentUser = SessionManager.getInstance().getCurrentUser();
            welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }
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
            Scene scene = dashboardButton.getScene();

            // Set the new root for the scene
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during loading
        }
    }
    private void setActiveButton(Button button) {
        // Remove active class from all buttons
        dataSiswaButton.getStyleClass().remove("active");
        dataKursusButton.getStyleClass().remove("active");
        dataTutorButton.getStyleClass().remove("active");
        dataTutorialButton.getStyleClass().remove("active");
        logoutButton.getStyleClass().remove("active");

        // Add active class to the specified button
        button.getStyleClass().add("active");
    }
}
