package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.domain.*;
import com.example.aplikasikursus.repository.*;
import com.example.aplikasikursus.result.Result;
import com.example.aplikasikursus.service.*;
import com.example.aplikasikursus.session.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TutorialController implements Initializable {
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
    private Tutorial selectedTutorial;
    private CoursesService coursesService;
    private SiswaService siswaService;
    private TutorService tutorService;
    private TutorialService tutorialService;
    private TutorialDetailService tutorialDetailService;
    private static final int ITEMS_PER_PAGE = 5;

    public TutorialController() {
        SiswaRepository siswaRepository = new SiswaRepository();
        this.siswaService = new SiswaService(siswaRepository);
        TutorRepository tutorRepository = new TutorRepository();
        this.tutorService = new TutorService(tutorRepository);
        CoursesRepository coursesRepository = new CoursesRepository();
        this.coursesService = new CoursesService(coursesRepository);
        TutorialRepository tutorialRepository = new TutorialRepository();
        this.tutorialService = new TutorialService(tutorialRepository);
        TutorialDetailRepository tutorialDetailRepository = new TutorialDetailRepository();
        this.tutorialDetailService = new TutorialDetailService(tutorialDetailRepository, tutorialRepository);
    }
    @FXML
    private TableView<Tutorial> tutorialTable;
    @FXML
    private Button tambahTutorialButton;
    @FXML
    private TableColumn<Tutorial, String> namaSiswaColumn;
    @FXML
    private TableColumn<Tutorial, String> namaTutorColumn;
    @FXML
    private TableColumn<Tutorial, String> namaKursusColumn;
    @FXML
    private TableColumn<Tutorial, String> statusColumn;
    @FXML
    private TableColumn<Tutorial, Void> actionColumn;
    @FXML
    private Pagination tutorialPagination;
    @FXML
    private void handleTambahTutorialButton() {
        openAddDialog();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Empty method body, as the logic is already implemented in the other initialize() method
    }
    private void setTutorialData(List<Tutorial> tutorialList) {
        int pageCount = (int) Math.ceil((double) tutorialList.size() / ITEMS_PER_PAGE);
        tutorialPagination.setPageCount(pageCount);

        tutorialPagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, tutorialList.size());
            List<Tutorial> pageTutorialList = tutorialList.subList(fromIndex, toIndex);
            tutorialTable.getItems().setAll(pageTutorialList);
            return tutorialTable;
        });
    }

    @FXML
    public void initialize() {
        setActiveButton(dataTutorialButton);
        // Configure table columns
        namaSiswaColumn.setCellValueFactory(data -> {
            Tutorial tutorial = data.getValue();
            Result<Siswa> siswaResult = siswaService.findById(tutorial.getSiswaId());
            Siswa siswa = siswaResult.getObject();
            String namaSiswa = siswa != null ? siswa.getNama() : "N/A";
            return new SimpleStringProperty(namaSiswa);
        });

        namaTutorColumn.setCellValueFactory(data -> {
            Tutorial tutorial = data.getValue();
            Result<Tutor> tutorResult = tutorService.findById(tutorial.getTutorId());
            Tutor tutor = tutorResult.getObject();
            String namaTutor = tutor != null ? tutor.getNama() : "N/A";
            return new SimpleStringProperty(namaTutor);
        });

        namaKursusColumn.setCellValueFactory(data -> {
            Tutorial tutorial = data.getValue();
            Result<Courses> coursesResult = coursesService.findById(tutorial.getCoursesId());
            Courses courses = coursesResult.getObject();
            String namaKursus = courses != null ? courses.getNamaKursus() : "N/A";
            return new SimpleStringProperty(namaKursus);
        });
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
                        Tutorial tutorial = getTableView().getItems().get(getIndex());
                        HBox buttonsContainer = new HBox(5);
                        Button infoButton = createInfoButton(tutorial);
                        Button editButton = createEditButton(tutorial);
                        Button deleteButton = createDeleteButton(tutorial);
                        buttonsContainer.getChildren().addAll(infoButton, editButton, deleteButton);
                        setGraphic(buttonsContainer);
                    }
                }
            };
        });

        // Fetch data from the coursesService and populate the table
        List<Tutorial> tutorialList = tutorialService.findAllActive();
        setTutorialData(tutorialList);
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

    @FXML
    private void handleHistoryButton(ActionEvent event) {
        loadPage("/com/example/aplikasikursus/view/dashboardadmin/tutorialHistory.fxml");
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
    private Button createInfoButton(Tutorial tutorial) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/info-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(15.0);
        button.setGraphic(imageView);
        button.setOnAction(event -> {
            // Handle info button action here
            openDetailDialog(tutorial);
        });
        return button;
    }

    private Button createEditButton(Tutorial tutorial) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/pen-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(20.0);
        button.setGraphic(imageView);
        button.setOnAction(event -> {
            selectedTutorial = tutorial;

            // Open the edit dialog or perform any other edit action
            try {
                openEditDialog();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return button;
    }

    private Button createDeleteButton(Tutorial tutorial) {
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
                Result<String> deleteResult = tutorialService.deleteById(tutorial.getId());
                if (!deleteResult.isError()) {
                    showSuccessAlert(deleteResult.getMessage());
                    refreshTutorialTable(); // Perbarui tabel untuk merefleksikan perubahan
                } else {
                    showErrorAlert(deleteResult.getMessage());
                }
            }
        });
        return button;
    }


    private void openDetailDialog(Tutorial selectedTutorial) {
        // Membuat TabPane untuk konten dialog
        TabPane tabPane = new TabPane();
        List<TutorialDetail> tutorialDetailList = tutorialDetailService.findByTutorialId(selectedTutorial.getId());
        int jumlahPertemuan = tutorialDetailList.size();
        // Tab untuk Detail Tutorial
        Tab detailTab = new Tab("Detail Tutorial");
        GridPane gridPane = createGridPaneForDetail(selectedTutorial, jumlahPertemuan, tabPane);
        detailTab.setContent(gridPane);
        tabPane.getTabs().add(detailTab);

        for (int i = 1; i <= jumlahPertemuan; i++) {
            Tab pertemuanTab = new Tab("Pertemuan " + i);
            GridPane pertemuanGridPane = createGridPaneForPertemuan(tutorialDetailList, i);
            pertemuanTab.setContent(pertemuanGridPane);
            tabPane.getTabs().add(pertemuanTab);
        }

        // Set the preferred size and constraints for the TabPane
        tabPane.setPrefSize(600, 400); // Set the desired width and height
        tabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow TabPane to expand

        // Membuat dialog dengan konten TabPane
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Detail Tutorial");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(tabPane);

        // Menambahkan tombol OK pada dialog
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        // Tampilkan dialog
        dialog.showAndWait();
    }

    private GridPane createGridPaneForDetail(Tutorial selectedTutorial, int jumlahPertemuan, TabPane tabPane) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        GridPane.setColumnSpan(gridPane, 2); // Set column span for the gridPane

        GridPane leftColumnPane = new GridPane();
        leftColumnPane.setHgap(10);
        leftColumnPane.setVgap(10);

        GridPane rightColumnPane = new GridPane();
        rightColumnPane.setHgap(10);
        rightColumnPane.setVgap(10);

        // Ambil data siswa berdasarkan siswaId dari TutorialService
        Result<Siswa> siswaResult = siswaService.findById(selectedTutorial.getSiswaId());
        Siswa siswa = siswaResult.getObject();
        String namaSiswa = siswa != null ? siswa.getNama() : "N/A";

        // Ambil data tutor berdasarkan tutorId dari TutorialService
        Result<Tutor> tutorResult = tutorService.findById(selectedTutorial.getTutorId());
        Tutor tutor = tutorResult.getObject();
        String namaTutor = tutor != null ? tutor.getNama() : "N/A";

        // Ambil data kursus berdasarkan coursesId dari TutorialService
        Result<Courses> coursesResult = coursesService.findById(selectedTutorial.getCoursesId());
        Courses courses = coursesResult.getObject();
        String namaKursus = courses != null ? courses.getNamaKursus() : "N/A";

        // Create labels and values
        Label[] labels = {
                new Label("Nama Siswa:"),
                new Label("Nama Tutor:"),
                new Label("Nama Kursus:"),
                new Label("Term:"),
                new Label("Tanggal Mulai Term:"),
                new Label("Nilai Akhir:"),
        };

        Label[] values = {
                new Label(namaSiswa),
                new Label(namaTutor),
                new Label(namaKursus),
                new Label(String.valueOf(selectedTutorial.getTerm())),
                new Label(String.valueOf(selectedTutorial.getTanggalMulaiTerm())),
                new Label(String.valueOf(selectedTutorial.getNilaiAkhir()))
        };

        // Add labels and values to left column pane
        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            leftColumnPane.add(labels[i], 0, row);
            leftColumnPane.add(values[i], 1, row);
            row++;
        }
        Label[] additionalLabels = {
                new Label("Jam Kehadiran:"),
                new Label("Catatan Tutor:"),
                new Label("Status:"),
                new Label("Created At:"),
                new Label("Updated At:"),
                new Label("Deactivate At:")
        };

        Label[] additionalValues = {
                new Label(String.valueOf(selectedTutorial.getJamKehadiran())),
                new Label(selectedTutorial.getCatatanTutor()),
                new Label(selectedTutorial.getStatus()),
                new Label(selectedTutorial.getCreatedAt() != null ? selectedTutorial.getCreatedAt().toString() : "N/A"),
                new Label(selectedTutorial.getUpdatedAt() != null ? selectedTutorial.getUpdatedAt().toString() : "N/A"),
                new Label(selectedTutorial.getDeactivateAt() != null ? selectedTutorial.getDeactivateAt().toString() : "N/A")
        };

        // Add additional labels and values to right column pane
        int additionalRow = 0;
        for (int i = 0; i < additionalLabels.length; i++) {
            rightColumnPane.add(additionalLabels[i], 0, additionalRow);
            rightColumnPane.add(additionalValues[i], 1, additionalRow);
            additionalRow++;
        }

        // Add additional labels and values if jumlahPertemuan < 3
        if (jumlahPertemuan < 3) {
            // Show the "Absensi" button
            Button absensiButton = new Button("Absensi");
            leftColumnPane.add(absensiButton, 0, row+1, 2, 1);
            absensiButton.setOnAction(event -> {
                // Create a new tab for the absensi form
                Tab absensiTab = new Tab("Absensi");
                ScrollPane absensiScrollPane = createAbsensiGridPane(selectedTutorial, tabPane);
                absensiTab.setContent(absensiScrollPane);
                tabPane.getTabs().add(absensiTab);

                // Select the newly created absensi tab
                tabPane.getSelectionModel().select(absensiTab);
            });
        }

        // Add the left and right column panes to the gridPane
        gridPane.add(leftColumnPane, 0, 0);
        gridPane.add(rightColumnPane, 1, 0);

        return gridPane;
    }
    private ScrollPane createAbsensiGridPane(Tutorial selectedTutorial, TabPane tabPane) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create JavaFX controls for the form fields
        ComboBox<String> sesiComboBox = new ComboBox<>();
        sesiComboBox.getItems().addAll("OL", "ON");
        sesiComboBox.setValue("OL"); // Set default value
        int pertemuanValue = tutorialDetailService.countPertemuanByTutorialId(selectedTutorial.getId()) + 1;
        TextField pertemuanField = new TextField(Integer.toString(pertemuanValue));
        pertemuanField.setEditable(false);

        TextField durasiField = new TextField();

        DatePicker tanggalTutorialPicker = new DatePicker();

        TextField jamMulaiField = new TextField();

        TextField jamSelesaiField = new TextField();

        TextArea realisasiMateriArea = new TextArea();

        TextArea realisasiPrArea = new TextArea();

        ComboBox<Integer> ratingTutorComboBox = new ComboBox<>();
        ratingTutorComboBox.getItems().addAll(1,2,3,4,5);

        ComboBox<Integer> ratingSiswaComboBox = new ComboBox<>();
        ratingSiswaComboBox.getItems().addAll(1,2,3,4,5);

        TextField nilaiField = new TextField();

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("A", "D");

        Label sesiLabel = new Label("Sesi:");
        Label pertemuanLabel = new Label("Pertemuan:");
        Label durasiLabel = new Label("Durasi (menit):");
        Label tanggalTutorialLabel = new Label("Tanggal Tutorial:");
        Label jamMulaiLabel = new Label("Jam Mulai (09:00:00):");
        Label jamSelesaiLabel = new Label("Jam Selesai (10:00:00):");
        Label realisasiMateriLabel = new Label("Realisasi Materi:");
        Label realisasiPrLabel = new Label("Realisasi PR:");
        Label ratingTutorLabel = new Label("Rating Tutor:");
        Label ratingSiswaLabel = new Label("Rating Siswa:");
        Label nilaiLabel = new Label("Nilai:");
        Label statusLabel = new Label("Status:");

// Set width constraints for labels
        sesiLabel.setMaxWidth(Double.MAX_VALUE);
        pertemuanLabel.setMaxWidth(Double.MAX_VALUE);
        durasiLabel.setMaxWidth(Double.MAX_VALUE);
        tanggalTutorialLabel.setMaxWidth(Double.MAX_VALUE);
        jamMulaiLabel.setMaxWidth(Double.MAX_VALUE);
        jamSelesaiLabel.setMaxWidth(Double.MAX_VALUE);
        realisasiMateriLabel.setMaxWidth(Double.MAX_VALUE);
        realisasiPrLabel.setMaxWidth(Double.MAX_VALUE);
        ratingTutorLabel.setMaxWidth(Double.MAX_VALUE);
        ratingSiswaLabel.setMaxWidth(Double.MAX_VALUE);
        nilaiLabel.setMaxWidth(Double.MAX_VALUE);
        statusLabel.setMaxWidth(Double.MAX_VALUE);

// Add the form fields to the GridPane
        gridPane.add(sesiLabel, 0, 0);
        gridPane.add(sesiComboBox, 1, 0, 2, 1);

        gridPane.add(pertemuanLabel, 0, 1);
        gridPane.add(pertemuanField, 1, 1, 2, 1);

        gridPane.add(durasiLabel, 0, 2);
        gridPane.add(durasiField, 1, 2, 2, 1);

        gridPane.add(tanggalTutorialLabel, 0, 3);
        gridPane.add(tanggalTutorialPicker, 1, 3, 2, 1);

        gridPane.add(jamMulaiLabel, 0, 4);
        gridPane.add(jamMulaiField, 1, 4, 2, 1);

        gridPane.add(jamSelesaiLabel, 0, 5);
        gridPane.add(jamSelesaiField, 1, 5, 2, 1);

        gridPane.add(realisasiMateriLabel, 0, 6);
        gridPane.add(realisasiMateriArea, 1, 6, 2, 1);

        gridPane.add(realisasiPrLabel, 0, 7);
        gridPane.add(realisasiPrArea, 1, 7, 2, 1);

        gridPane.add(ratingTutorLabel, 0, 8);
        gridPane.add(ratingTutorComboBox, 1, 8, 2, 1);

        gridPane.add(ratingSiswaLabel, 0, 9);
        gridPane.add(ratingSiswaComboBox, 1, 9, 2, 1);

        gridPane.add(nilaiLabel, 0, 10);
        gridPane.add(nilaiField, 1, 10, 2, 1);

        gridPane.add(statusLabel, 0, 11);
        gridPane.add(statusComboBox, 1, 11, 2, 1);

// Set the column span for the labels that were not visible
        GridPane.setHalignment(sesiLabel, HPos.RIGHT);
        GridPane.setHalignment(pertemuanLabel, HPos.RIGHT);
        GridPane.setHalignment(durasiLabel, HPos.RIGHT);
        GridPane.setHalignment(tanggalTutorialLabel, HPos.RIGHT);
        GridPane.setHalignment(jamMulaiLabel, HPos.RIGHT);
        GridPane.setHalignment(jamSelesaiLabel, HPos.RIGHT);

// Set column widths
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(33.33);
        col2.setPercentWidth(33.33);
        col3.setPercentWidth(33.33);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        Button saveButton = new Button("Save");

        TextField nilaiAkhir = new TextField();
        TextField kehadiran = new TextField();
        TextField jamKehadiran = new TextField();
        TextArea catatanTutor = new TextArea();

        if (pertemuanValue == 3) {
            statusComboBox.setValue("D");
            statusComboBox.setEditable(false);
//            statusComboBox.setOnAction(event -> {
//                statusComboBox.setValue("D"); // Reset the value to "D" if the user tries to change it
//            });
            Label nilaiAkhirLabel = new Label("Nilai Akhir:");
            Label kehadiranLabel = new Label("Kehadiran (jumlah kehadiran):");
            Label jamKehadiranLabel = new Label("Jam Kehadiran (09:00:00):");
            Label catatanTutorLabel = new Label("Catatan tutor:");

            gridPane.add(nilaiAkhirLabel, 0, 12);
            gridPane.add(nilaiAkhir, 1, 12, 2, 1);

            gridPane.add(kehadiranLabel, 0, 13);
            gridPane.add(kehadiran, 1, 13, 2, 1);

            gridPane.add(jamKehadiranLabel, 0, 14);
            gridPane.add(jamKehadiran, 1, 14, 2, 1);

            gridPane.add(catatanTutorLabel, 0, 15);
            gridPane.add(catatanTutor, 1, 15, 2, 1);
            gridPane.add(saveButton, 1, 16, 2, 1);

            nilaiAkhirLabel.setMaxWidth(Double.MAX_VALUE);
            kehadiranLabel.setMaxWidth(Double.MAX_VALUE);
            jamKehadiranLabel.setMaxWidth(Double.MAX_VALUE);
            catatanTutorLabel.setMaxWidth(Double.MAX_VALUE);
        } else {
            // Menambahkan tombol "Save" ke dalam GridPane
            gridPane.add(saveButton, 1, 12, 2, 1);
        }
// Mengatur tindakan saat tombol "Save" ditekan
        saveButton.setOnAction(event -> {
            int tutorialId = selectedTutorial.getId();
            String sesi = sesiComboBox.getValue();
            int pertemuan = Integer.parseInt(pertemuanField.getText());
            int durasi = Integer.parseInt(durasiField.getText());
            String tanggalTutorialText = tanggalTutorialPicker.getValue().toString();
            Date tanggalTutorial = Date.valueOf(tanggalTutorialText);
            String jamMulaiText = jamMulaiField.getText();
            Time jamMulai = Time.valueOf(jamMulaiText);
            String jamSelesaiText = jamSelesaiField.getText();
            Time jamSelesai = Time.valueOf(jamSelesaiText);
            String realisasiMateri = realisasiMateriArea.getText();
            String realisasiPr = realisasiPrArea.getText();
            Integer ratingTutor = ratingTutorComboBox.getValue();
            Integer ratingSiswa = ratingSiswaComboBox.getValue();
            float nilai = Float.parseFloat(nilaiField.getText());
            Akun currentUser = SessionManager.getInstance().getCurrentUser();
            String diinputOleh = currentUser.getUsername();
            String status = statusComboBox.getValue();

            TutorialDetail tutorialDetail = new TutorialDetail(tutorialId, sesi, pertemuan, durasi, tanggalTutorial, jamMulai,
                    jamSelesai, realisasiMateri, realisasiPr,
                    ratingTutor, ratingSiswa, nilai,diinputOleh, status);
            Result<TutorialDetail> result;
            if (pertemuan == 3) {
                double nilaiAkhirValue = Double.parseDouble(nilaiAkhir.getText());
                int kehadiranValue = Integer.parseInt(kehadiran.getText());
                Time jamKehadiranValue = Time.valueOf(jamKehadiran.getText());
                String catatanTutorValue = catatanTutor.getText();

                selectedTutorial.setNilaiAkhir(nilaiAkhirValue);
                selectedTutorial.setKehadiran(kehadiranValue);
                selectedTutorial.setJamKehadiran(jamKehadiranValue);
                selectedTutorial.setCatatanTutor(catatanTutorValue);
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                if(selectedTutorial.getStatus().equals("D")) {
                    selectedTutorial.setDeactivateAt(currentTime);
                }
                selectedTutorial.setUpdatedAt(currentTime);
                result = tutorialDetailService.insertLastOne(tutorialDetail, selectedTutorial);

            } else {
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                if(selectedTutorial.getStatus().equals("D")) {
                    selectedTutorial.setDeactivateAt(currentTime);
                }
                result = tutorialDetailService.insertOne(tutorialDetail);
            }
            if (!result.isError()) {
                showSuccessAlert(result.getMessage());

                Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
                tabPane.getTabs().remove(currentTab);

                Tab newTab = new Tab("Pertemuan " + pertemuan);
                GridPane newGridPane = createGridPaneForPertemuan(Collections.singletonList(tutorialDetail), 1);
                newTab.setContent(newGridPane);
                tabPane.getTabs().add(newTab);
                tabPane.getSelectionModel().select(newTab); // Select the newly created tab

                refreshTutorialTable();
            } else {
                showErrorAlert(result.getMessage());
            }
        });


        // Create a ScrollPane and set the GridPane as its content
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Set the preferred size of the ScrollPane
        scrollPane.setPrefSize(400, 400);

        return scrollPane;
    }

    private GridPane createGridPaneForPertemuan(List<TutorialDetail> tutorialDetailList, int pertemuanIndex) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TutorialDetail pertemuan = tutorialDetailList.get(pertemuanIndex - 1);

        GridPane leftColumnPane = new GridPane();
        leftColumnPane.setHgap(10);
        leftColumnPane.setVgap(10);

        GridPane rightColumnPane = new GridPane();
        rightColumnPane.setHgap(10);
        rightColumnPane.setVgap(10);

        // Create labels and values
        Label[] labels = {
                new Label("Sesi:"),
                new Label("Pertemuan:"),
                new Label("Durasi:"),
                new Label("Tanggal Tutorial:")
        };

        Label[] values = {
                new Label(String.valueOf(pertemuan.getSesi())),
                new Label(String.valueOf(pertemuan.getPertemuan())),
                new Label(String.valueOf(pertemuan.getDurasi())),
                new Label(pertemuan.getTanggalTutorial().toString())
        };

        // Add labels and values to left column pane
        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            leftColumnPane.add(labels[i], 0, row);
            leftColumnPane.add(values[i], 1, row);
            row++;
        }

        // Create additional labels and values
        Label[] additionalLabels = {
                new Label("Jam Mulai:"),
                new Label("Jam Selesai:"),
                new Label("Realisasi Materi:"),
                new Label("Realisasi PR:")
        };

        Label[] additionalValues = {
                new Label(pertemuan.getJamMulai().toString()),
                new Label(pertemuan.getJamSelesai().toString()),
                new Label(pertemuan.getRealisasiMateri()),
                new Label(pertemuan.getRealisasiPr())
        };

        // Add additional labels and values to right column pane
        int additionalRow = 0;
        for (int i = 0; i < additionalLabels.length; i++) {
            rightColumnPane.add(additionalLabels[i], 0, additionalRow);
            rightColumnPane.add(additionalValues[i], 1, additionalRow);
            additionalRow++;
        }

        // Create remaining labels and values
        Label[] remainingLabels = {
                new Label("Rating Tutor:"),
                new Label("Rating Siswa:"),
                new Label("Nilai:"),
                new Label("Diinput Oleh:")
        };

        Label[] remainingValues = {
                new Label(String.valueOf(pertemuan.getRatingTutor())),
                new Label(String.valueOf(pertemuan.getRatingSiswa())),
                new Label(String.valueOf(pertemuan.getNilai())),
                new Label(pertemuan.getDiinputOleh())
        };

        // Add remaining labels and values to left column pane
        for (int i = 0; i < remainingLabels.length; i++) {
            leftColumnPane.add(remainingLabels[i], 0, row);
            leftColumnPane.add(remainingValues[i], 1, row);
            row++;
        }

        // Create additional remaining labels and values
        Label[] additionalRemainingLabels = {
                new Label("Status:"),
                new Label("Created At:"),
                new Label("Updated At:"),
                new Label("Deactivate At:")
        };

        Label[] additionalRemainingValues = {
                new Label(pertemuan.getStatus()),
                new Label(pertemuan.getCreatedAt() != null ? pertemuan.getCreatedAt().toString() : "N/A"),
                new Label(pertemuan.getUpdatedAt() != null ? pertemuan.getUpdatedAt().toString() : "N/A"),
                new Label(pertemuan.getDeactivateAt() != null ? pertemuan.getDeactivateAt().toString() : "N/A")
        };

        // Add additional remaining labels and values to right column pane
        for (int i = 0; i < additionalRemainingLabels.length; i++) {
            rightColumnPane.add(additionalRemainingLabels[i], 0, additionalRow);
            rightColumnPane.add(additionalRemainingValues[i], 1, additionalRow);
            additionalRow++;
        }

        // Add left and right column panes to main gridPane
        gridPane.add(leftColumnPane, 0, 0);
        gridPane.add(rightColumnPane, 1, 0);

        return gridPane;
    }

    private void openAddDialog() {
        // Membuat GridPane untuk konten dialog
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Menambahkan label dan ComboBox untuk Nama Siswa
        Label siswaLabel = new Label("Nama Siswa:");
        ComboBox<Siswa> siswaComboBox = new ComboBox<>();
        try {
            siswaComboBox.setItems(getAllActiveSiswa()); // Assumes you have a method to retrieve all active Siswa objects
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        siswaComboBox.setConverter(new StringConverter<Siswa>() {
            @Override
            public String toString(Siswa siswa) {
                return siswa != null ? siswa.getNama() : "";
            }

            @Override
            public Siswa fromString(String string) {
                return null;
            }
        });
        gridPane.add(siswaLabel, 0, 0);
        gridPane.add(siswaComboBox, 1, 0);

        // Menambahkan label dan ComboBox untuk Nama Tutor
        Label tutorLabel = new Label("Nama Tutor:");
        ComboBox<Tutor> tutorComboBox = new ComboBox<>();
        try {
            tutorComboBox.setItems(getAllActiveTutor()); // Assumes you have a method to retrieve all active Tutor objects
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tutorComboBox.setConverter(new StringConverter<Tutor>() {
            @Override
            public String toString(Tutor tutor) {
                return tutor != null ? tutor.getNama() : "";
            }

            @Override
            public Tutor fromString(String string) {
                return null;
            }
        });
        gridPane.add(tutorLabel, 0, 1);
        gridPane.add(tutorComboBox, 1, 1);

        // Menambahkan label dan ComboBox untuk Nama Courses
        Label coursesLabel = new Label("Nama Courses:");
        ComboBox<Courses> coursesComboBox = new ComboBox<>();
        try {
            coursesComboBox.setItems(getAllActiveCourses()); // Assumes you have a method to retrieve all active Courses objects
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        coursesComboBox.setConverter(new StringConverter<Courses>() {
            @Override
            public String toString(Courses courses) {
                return courses != null ? courses.getNamaKursus() : "";
            }

            @Override
            public Courses fromString(String string) {
                return null;
            }
        });
        gridPane.add(coursesLabel, 0, 2);
        gridPane.add(coursesComboBox, 1, 2);

        // Menambahkan label dan DatePicker untuk Tanggal Mulai Term
        Label tanggalMulaiTermLabel = new Label("Tanggal Mulai Term:");
        DatePicker tanggalMulaiTermPicker = new DatePicker();
        gridPane.add(tanggalMulaiTermLabel, 0, 3);
        gridPane.add(tanggalMulaiTermPicker, 1, 3);

        // Membuat dialog dengan konten GridPane
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Tambah Tutorial");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        // Menambahkan tombol-tombol OK dan Cancel pada dialog
        ButtonType okButton = new ButtonType("Tambah Tutorial", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Mengatur tindakan saat tombol OK ditekan
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                Siswa selectedSiswa = siswaComboBox.getValue();
                Tutor selectedTutor = tutorComboBox.getValue();
                Courses selectedCourses = coursesComboBox.getValue();
                LocalDate selectedDate = tanggalMulaiTermPicker.getValue();

                if (selectedSiswa != null && selectedTutor != null && selectedCourses != null && selectedDate != null) {
                    Tutorial newTutorial = new Tutorial();
                    newTutorial.setSiswaId(selectedSiswa.getId());
                    newTutorial.setTutorId(selectedTutor.getId());
                    newTutorial.setCoursesId(selectedCourses.getId());
                    newTutorial.setTanggalMulaiTerm(Date.valueOf(selectedDate));
                    newTutorial.setTerm(tutorialService.countTermBySiswaId(newTutorial.getSiswaId()) + 1);
                    // Tambahkan tutorial ke database
                    Result<String> result = tutorialService.insertOne(newTutorial);
                    if (!result.isError()) {
                        showSuccessAlert(result.getMessage());
                        refreshTutorialTable(); // Perbarui tabel untuk merefleksikan perubahan
                    } else {
                        showErrorAlert(result.getMessage());
                    }
                } else {
                    showErrorAlert("Mohon lengkapi semua inputan.");
                }
            }
            return null;
        });

        // Tampilkan dialog
        dialog.showAndWait();
    }
    private void openEditDialog() throws SQLException {
        // Open the dialog to edit tutorial details

        // Create a GridPane for the dialog content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add label and ComboBox for Siswa (Student) Name
        Label siswaLabel = new Label("Nama Siswa:");
        ComboBox<Siswa> siswaComboBox = new ComboBox<>();
        siswaComboBox.setItems(getAllActiveSiswa()); // Assumes you have a method to retrieve all active Siswa objects
        siswaComboBox.setConverter(new StringConverter<Siswa>() {
            @Override
            public String toString(Siswa siswa) {
                return siswa != null ? siswa.getNama() : "";
            }

            @Override
            public Siswa fromString(String string) {
                return null;
            }
        });
        Siswa currentSiswa = siswaService.findById(selectedTutorial.getSiswaId()).getObject();
        siswaComboBox.setValue(currentSiswa); // Set the initial selected value
        gridPane.add(siswaLabel, 0, 0);
        gridPane.add(siswaComboBox, 1, 0);

        // Add label and ComboBox for Tutor Name
        Label tutorLabel = new Label("Nama Tutor:");
        ComboBox<Tutor> tutorComboBox = new ComboBox<>();
        tutorComboBox.setItems(getAllActiveTutor()); // Assumes you have a method to retrieve all active Tutor objects
        tutorComboBox.setConverter(new StringConverter<Tutor>() {
            @Override
            public String toString(Tutor tutor) {
                return tutor != null ? tutor.getNama() : "";
            }

            @Override
            public Tutor fromString(String string) {
                return null;
            }
        });
        Tutor currentTutor = tutorService.findById(selectedTutorial.getTutorId()).getObject();
        tutorComboBox.setValue(currentTutor); // Set the initial selected value
        gridPane.add(tutorLabel, 0, 1);
        gridPane.add(tutorComboBox, 1, 1);

        // Add label and ComboBox for Courses Name
        Label coursesLabel = new Label("Nama Courses:");
        ComboBox<Courses> coursesComboBox = new ComboBox<>();
        coursesComboBox.setItems(getAllActiveCourses()); // Assumes you have a method to retrieve all active Courses objects
        coursesComboBox.setConverter(new StringConverter<Courses>() {
            @Override
            public String toString(Courses courses) {
                return courses != null ? courses.getNamaKursus() : "";
            }

            @Override
            public Courses fromString(String string) {
                return null;
            }
        });
        Courses currentCourses = coursesService.findById(selectedTutorial.getCoursesId()).getObject();
        coursesComboBox.setValue(currentCourses); // Set the initial selected value
        gridPane.add(coursesLabel, 0, 2);
        gridPane.add(coursesComboBox, 1, 2);

        // Add label and DatePicker for Start Date of Term
        Label tanggalMulaiTermLabel = new Label("Tanggal Mulai Term:");
        DatePicker tanggalMulaiTermPicker = new DatePicker();
        java.util.Date tanggalMulaiTermDate = selectedTutorial.getTanggalMulaiTerm();
        Instant instant = tanggalMulaiTermDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate tanggalMulaiTermLocalDate = instant.atZone(zoneId).toLocalDate();

        tanggalMulaiTermPicker.setValue(tanggalMulaiTermLocalDate);// Set the initial selected value
        gridPane.add(tanggalMulaiTermLabel, 0, 3);
        gridPane.add(tanggalMulaiTermPicker, 1, 3);

        Label statusLabel = new Label("Status:");
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("A", "H");
        gridPane.add(statusLabel, 0, 4);
        gridPane.add(statusComboBox, 1, 4);
        statusComboBox.setValue(selectedTutorial.getStatus());

        // Create the dialog with the GridPane content
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Tutorial");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        // Add OK and Cancel buttons to the dialog
        ButtonType okButton = new ButtonType("Edit Tutorial", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Set the action when OK button is pressed
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                Siswa selectedSiswa = siswaComboBox.getValue();
                Tutor selectedTutor = tutorComboBox.getValue();
                Courses selectedCourses = coursesComboBox.getValue();
                LocalDate selectedDate = tanggalMulaiTermPicker.getValue();
                String selectedStatus = statusComboBox.getValue();

                if (selectedSiswa != null && selectedTutor != null && selectedCourses != null && selectedDate != null) {
                    selectedTutorial.setSiswaId(selectedSiswa.getId());
                    selectedTutorial.setTutorId(selectedTutor.getId());
                    selectedTutorial.setCoursesId(selectedCourses.getId());
                    selectedTutorial.setTanggalMulaiTerm(Date.valueOf(selectedDate));
                    selectedTutorial.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    selectedTutorial.setStatus(selectedStatus);

                    // Update the tutorial in the database
                    Result<String> result = tutorialService.updateById(selectedTutorial.getId(), selectedTutorial);
                    if (!result.isError()) {
                        refreshTutorialTable(); // Update the table to reflect the changes
                        showSuccessAlert(result.getMessage());
                    } else {
                        showErrorAlert(result.getMessage());
                    }
                } else {
                    showErrorAlert("Mohon lengkapi semua inputan.");
                }
            }
            return null;
        });

        // Show the dialog
        dialog.showAndWait();
    }

    private ObservableList<Siswa> getAllActiveSiswa() throws SQLException {
        List<Siswa> activeSiswaList = siswaService.findAllActive();
        ObservableList<Siswa> activeSiswaObservableList = FXCollections.observableArrayList();
        activeSiswaObservableList.addAll(activeSiswaList);
        return activeSiswaObservableList;
    }

    public ObservableList<Tutor> getAllActiveTutor() throws SQLException {
        List<Tutor> activeTutorList = tutorService.findAllActive();
        ObservableList<Tutor> activeTutorObservableList = FXCollections.observableArrayList();
        activeTutorObservableList.addAll(activeTutorList);
        return activeTutorObservableList;
    }

    public ObservableList<Courses> getAllActiveCourses() throws SQLException {
        List<Courses> activeCoursesList = coursesService.findAllActive();
        ObservableList<Courses> activeCoursesObservableList = FXCollections.observableArrayList();
        activeCoursesObservableList.addAll(activeCoursesList);
        return activeCoursesObservableList;
    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
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

    private void refreshTutorialTable() {
        // Assuming you have a TableView named tutorialTable
        tutorialTable.getItems().clear();
        tutorialTable.getItems().addAll(tutorialService.findAllActive());
    }

}
