package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.domain.Courses;
import com.example.aplikasikursus.repository.CoursesRepository;
import com.example.aplikasikursus.result.Result;
import com.example.aplikasikursus.service.CoursesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {
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
    private Courses selectedCourses;
    private CoursesService coursesService;
    @FXML
    private TextField searchCoursesInput;
    @FXML
    private Button searchCoursesButton;
    @FXML
    private Button tambahCoursesButton;
    private static final int ITEMS_PER_PAGE = 5;
    @FXML
    private TableView<Courses> coursesTable;
    @FXML
    private Pagination coursesPagination;
    @FXML
    private TableColumn<Courses, String> namaColumn;
    @FXML
    private TableColumn<Courses, String> hargaColumn;
    @FXML
    private TableColumn<Courses, String> statusColumn;
    @FXML
    private TableColumn<Courses, Void> actionColumn;

    public CoursesController () {
        CoursesRepository coursesRepository = new CoursesRepository();
        this.coursesService = new CoursesService(coursesRepository);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Empty method body, as the logic is already implemented in the other initialize() method
    }
    @FXML
    public void initialize() {
        setActiveButton(dataKursusButton);
        searchCoursesButton.setOnAction(this::handleSearchCourses);
        // Configure table columns
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("namaKursus"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add custom cell factory to action column
        actionColumn.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Courses courses = getTableView().getItems().get(getIndex());
                        HBox buttonsContainer = new HBox(5);
                        Button infoButton = createInfoButton(courses);
                        Button editButton = createEditButton(courses);
                        Button deleteButton = createDeleteButton(courses);
                        buttonsContainer.getChildren().addAll(infoButton, editButton, deleteButton);
                        setGraphic(buttonsContainer);
                    }
                }
            };
        });

        // Fetch data from the coursesService and populate the table
        try {
            List<Courses> coursesList = coursesService.findAll("");
            setCoursesData(coursesList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

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

    private void setCoursesData(List<Courses> coursesList) {
        int pageCount = (int) Math.ceil((double) coursesList.size() / ITEMS_PER_PAGE);
        coursesPagination.setPageCount(pageCount);

        coursesPagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, coursesList.size());
            List<Courses> pageCoursesList = coursesList.subList(fromIndex, toIndex);
            coursesTable.getItems().setAll(pageCoursesList);
            return coursesTable;
        });
    }

    private Button createInfoButton(Courses courses) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/info-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(15.0);
        button.setGraphic(imageView);
        button.setOnAction(event -> {
            // Handle info button action here
            openDetailDialog(courses);
        });
        return button;
    }

    private Button createEditButton(Courses courses) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/pen-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(20.0);
        button.setGraphic(imageView);
        button.setOnAction(event -> {
            selectedCourses = courses;

            // Open the edit dialog or perform any other edit action
            openEditDialog();
        });
        return button;
    }

    private Button createDeleteButton(Courses courses) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/trash-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(15.0);
        button.setGraphic(imageView);
        button.setOnAction(event -> {
            // Handle delete button action here
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this course?");

            // Customize the buttons in the confirmation dialog
            ButtonType confirmButton = new ButtonType("Delete");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Wait for user action and process accordingly
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                // User confirmed deletion, proceed with delete action
                Result<String> deleteResult = coursesService.deleteById(courses.getId());
                if (!deleteResult.isError()) {
                    showSuccessAlert(deleteResult.getMessage());
                    refreshCoursesTable(); // Perbarui tabel untuk merefleksikan perubahan
                } else {
                    showErrorAlert(deleteResult.getMessage());
                }
            }
        });
        return button;
    }

    @FXML
    private void handleSearchCourses(ActionEvent event) {
        String searchQuery = searchCoursesInput.getText();
        try {
            List<Courses> searchResults = coursesService.findAll(searchQuery);
            setCoursesData(searchResults);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void openEditDialog() {
        // Buka dialog untuk mengedit detail kursus

        // Membuat GridPane untuk konten dialog
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Menambahkan label dan input untuk Nama Kursus
        Label namaKursusLabel = new Label("Nama Kursus:");
        TextField namaKursusInput = new TextField(selectedCourses.getNamaKursus());
        gridPane.add(namaKursusLabel, 0, 0);
        gridPane.add(namaKursusInput, 1, 0);

        // Menambahkan label dan input untuk Harga Kursus
        Label hargaKursusLabel = new Label("Harga Kursus:");
        TextField hargaKursusInput = new TextField(String.valueOf(selectedCourses.getHarga()));
        gridPane.add(hargaKursusLabel, 0, 1);
        gridPane.add(hargaKursusInput, 1, 1);

        // Menambahkan label dan ComboBox untuk Status Kursus
        Label statusKursusLabel = new Label("Status Kursus:");
        ComboBox<String> statusKursusComboBox = new ComboBox<>();
        statusKursusComboBox.getItems().addAll("Active", "Deactivate");
        statusKursusComboBox.setValue("Active/Deactivate");
        gridPane.add(statusKursusLabel, 0, 2);
        gridPane.add(statusKursusComboBox, 1, 2);

        // Membuat dialog dengan konten GridPane
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Kursus");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        // Menambahkan tombol-tombol OK dan Cancel pada dialog
        ButtonType okButton = new ButtonType("Edit Kursus", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Mengatur tindakan saat tombol OK ditekan
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                String newNamaKursus = namaKursusInput.getText();
                double newHargaKursus = Double.parseDouble(hargaKursusInput.getText());
                String newStatusKursus = statusKursusComboBox.getValue();

                selectedCourses.setNamaKursus(newNamaKursus);
                selectedCourses.setHarga(newHargaKursus);
                selectedCourses.setStatus(newStatusKursus);

                // Perbarui kursus di database
                Result<String> result = coursesService.updateById(selectedCourses.getId(), selectedCourses);
                if (!result.isError()) {
                    refreshCoursesTable(); // Perbarui tabel untuk merefleksikan perubahan
                    showSuccessAlert(result.getMessage());
                } else {
                    showErrorAlert(result.getMessage());
                }
            }
            return null;
        });

        // Tampilkan dialog
        dialog.showAndWait();
    }



    private void refreshCoursesTable() {
        try {
            List<Courses> coursesList = coursesService.findAll("");
            setCoursesData(coursesList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void openAddDialog() {
        // Membuat GridPane untuk konten dialog
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Menambahkan label dan input untuk Nama Kursus
        Label namaKursusLabel = new Label("Nama Kursus:");
        TextField namaKursusInput = new TextField();
        gridPane.add(namaKursusLabel, 0, 0);
        gridPane.add(namaKursusInput, 1, 0);

        // Menambahkan label dan input untuk Harga Kursus
        Label hargaKursusLabel = new Label("Harga Kursus:");
        TextField hargaKursusInput = new TextField();
        gridPane.add(hargaKursusLabel, 0, 1);
        gridPane.add(hargaKursusInput, 1, 1);

        // Menambahkan label dan ComboBox untuk Status Kursus
        Label statusKursusLabel = new Label("Status Kursus:");
        ComboBox<String> statusKursusComboBox = new ComboBox<>();
        statusKursusComboBox.getItems().addAll("Active", "Deactivate");
        statusKursusComboBox.setValue("Active");
        gridPane.add(statusKursusLabel, 0, 2);
        gridPane.add(statusKursusComboBox, 1, 2);

        // Membuat dialog dengan konten GridPane
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Tambah Kursus");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        // Menambahkan tombol-tombol OK dan Cancel pada dialog
        ButtonType okButton = new ButtonType("Tambah Kursus", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Mengatur tindakan saat tombol OK ditekan
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                String newNamaKursus = namaKursusInput.getText();
                double newHargaKursus = Double.parseDouble(hargaKursusInput.getText());
                String newStatusKursus = statusKursusComboBox.getValue();

                Courses newCourses = new Courses(newNamaKursus, newHargaKursus, newStatusKursus);
                newCourses.setNamaKursus(newNamaKursus);
                newCourses.setHarga(newHargaKursus);
                newCourses.setStatus(newStatusKursus);

                // Tambahkan kursus ke database
                Result<String> result = coursesService.insertOne(newCourses);
                if (!result.isError()) {
                    showSuccessAlert(result.getMessage());
                    refreshCoursesTable(); // Perbarui tabel untuk merefleksikan perubahan
                } else {
                    showErrorAlert(result.getMessage());
                }
                refreshCoursesTable(); // Perbarui tabel untuk merefleksikan perubahan
            }
            return null;
        });

        // Tampilkan dialog
        dialog.showAndWait();
    }
    @FXML
    private void handleTambahCoursesButton() {
        openAddDialog();
    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openDetailDialog(Courses selectedCourses) {
        // Buka dialog untuk melihat detail kursus

        // Membuat GridPane untuk konten dialog
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Menambahkan label dan nilai untuk Nama Kursus
        Label namaKursusLabel = new Label("Nama Kursus:");
        Label namaKursusValue = new Label(selectedCourses.getNamaKursus());
        gridPane.add(namaKursusLabel, 0, 0);
        gridPane.add(namaKursusValue, 1, 0);

        // Menambahkan label dan nilai untuk Harga Kursus
        Label hargaKursusLabel = new Label("Harga Kursus:");
        Label hargaKursusValue = new Label(String.valueOf(selectedCourses.getHarga()));
        gridPane.add(hargaKursusLabel, 0, 1);
        gridPane.add(hargaKursusValue, 1, 1);

        // Menambahkan label dan nilai untuk Status Kursus
        Label statusKursusLabel = new Label("Status Kursus:");
        Label statusKursusValue = new Label(selectedCourses.getStatus());
        gridPane.add(statusKursusLabel, 0, 2);
        gridPane.add(statusKursusValue, 1, 2);

        // Menambahkan label dan nilai untuk Tanggal Dibuat
        Label createdAtLabel = new Label("Tanggal Dibuat:");
        Label createdAtValue = new Label(selectedCourses.getCreatedAt() != null ? selectedCourses.getCreatedAt().toString() : "N/A");
        gridPane.add(createdAtLabel, 0, 3);
        gridPane.add(createdAtValue, 1, 3);

        // Menambahkan label dan nilai untuk Tanggal Diperbarui
        Label updatedAtLabel = new Label("Tanggal Diperbarui:");
        Label updatedAtValue = new Label(selectedCourses.getUpdatedAt() != null ? selectedCourses.getUpdatedAt().toString() : "N/A");
        gridPane.add(updatedAtLabel, 0, 4);
        gridPane.add(updatedAtValue, 1, 4);

        // Menambahkan label dan nilai untuk Tanggal Dinonaktifkan
        Label deactivateAtLabel = new Label("Tanggal Dinonaktifkan:");
        Label deactivateAtValue = new Label(selectedCourses.getDeactivateAt() != null ? selectedCourses.getDeactivateAt().toString() : "N/A");
        gridPane.add(deactivateAtLabel, 0, 5);
        gridPane.add(deactivateAtValue, 1, 5);

        // Membuat dialog dengan konten GridPane
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Detail Kursus");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        // Menambahkan tombol OK pada dialog
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        // Tampilkan dialog
        dialog.showAndWait();
    }
}