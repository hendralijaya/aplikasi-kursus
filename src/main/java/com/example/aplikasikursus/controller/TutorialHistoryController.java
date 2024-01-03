package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.domain.*;
import com.example.aplikasikursus.repository.*;
import com.example.aplikasikursus.result.Result;
import com.example.aplikasikursus.service.*;
import com.example.aplikasikursus.utils.PdfGenerator;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TutorialHistoryController implements Initializable {
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
    private CoursesService coursesService;
    private SiswaService siswaService;
    private TutorService tutorService;
    private TutorialService tutorialService;
    private TutorialDetailService tutorialDetailService;
    private static final int ITEMS_PER_PAGE = 5;
    public TutorialHistoryController() {
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
    private TableView<Tutorial> tutorialHistoryTable;
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
    private Pagination tutorialHistoryPagination;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Empty method body, as the logic is already implemented in the other initialize() method
    }
    private void refreshTutorialTable() {
        // Assuming you have a TableView named tutorialTable
        tutorialHistoryTable.getItems().clear();
        tutorialHistoryTable.getItems().addAll(tutorialService.findAllDeactivate());
    }

    private void setTutorialHistoryData(List<Tutorial> tutorialList) {
        int pageCount = (int) Math.ceil((double) tutorialList.size() / ITEMS_PER_PAGE);
        tutorialHistoryPagination.setPageCount(pageCount);

        tutorialHistoryPagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, tutorialList.size());
            List<Tutorial> pageTutorialList = tutorialList.subList(fromIndex, toIndex);
            tutorialHistoryTable.getItems().setAll(pageTutorialList);
            return tutorialHistoryTable;
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
                        Button pdfReportButton = createPDFReportButton(tutorial);
                        Button deleteButton = createDeleteButton(tutorial);
                        buttonsContainer.getChildren().addAll(infoButton, pdfReportButton, deleteButton);
                        setGraphic(buttonsContainer);
                    }
                }
            };
        });

        // Fetch data from the coursesService and populate the table
        List<Tutorial> tutorialList = tutorialService.findAllDeactivate();
        setTutorialHistoryData(tutorialList);
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

    private Button createPDFReportButton(Tutorial tutorial) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/file-pdf-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(15.0);
        button.setGraphic(imageView);
        button.setOnAction(event -> {
            // Handle info button action here
            PdfGenerator pdfGenerator = new PdfGenerator(
                    tutorialService, tutorialDetailService, siswaService, tutorService, coursesService);
            pdfGenerator.generatePdfReport(tutorial);
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
        // Add the left and right column panes to the gridPane
        gridPane.add(leftColumnPane, 0, 0);
        gridPane.add(rightColumnPane, 1, 0);

        return gridPane;
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
}
