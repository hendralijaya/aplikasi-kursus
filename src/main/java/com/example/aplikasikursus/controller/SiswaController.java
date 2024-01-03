package com.example.aplikasikursus.controller;

import com.example.aplikasikursus.domain.Siswa;
import com.example.aplikasikursus.repository.SiswaRepository;
import com.example.aplikasikursus.service.SiswaService;
import javafx.beans.binding.Bindings;
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
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class SiswaController implements Initializable {
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
    private TextField searchSiswaInput;
    @FXML
    private Button searchSiswaButton;
    private static final int ITEMS_PER_PAGE = 5;
    @FXML
    private TableView<Siswa> siswaTable;
    @FXML
    private Pagination siswaPagination;
    @FXML
    private TableColumn<Siswa, String> namaColumn;
    @FXML
    private TableColumn<Siswa, String> emailColumn;
    @FXML
    private TableColumn<Siswa, String> namaSekolahUniversitasColumn;
    @FXML
    private TableColumn<Siswa, String> statusColumn;
    @FXML
    private TableColumn<Siswa, Void> actionColumn;
    private SiswaService siswaService;
    public SiswaController() {
        SiswaRepository siswaRepository = new SiswaRepository();
        this.siswaService = new SiswaService(siswaRepository);
    }
    public void setSiswaData(List<Siswa> siswaList) {
        int pageCount = (int) Math.ceil((double) siswaList.size() / ITEMS_PER_PAGE);
        siswaPagination.setPageCount(pageCount);

        siswaPagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, siswaList.size());
            List<Siswa> pageSiswaList = siswaList.subList(fromIndex, toIndex);
            siswaTable.getItems().setAll(pageSiswaList);
            return siswaTable;
        });
    }

    @FXML
    public void initialize() {
        setActiveButton(dataSiswaButton);
        searchSiswaButton.setOnAction(this::handleSearchSiswa);
        // Configure table columns
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        namaSekolahUniversitasColumn.setCellValueFactory(new PropertyValueFactory<>("namaSekolahUniversitas"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        actionColumn.setCellFactory(column -> {
            return new TableCell<>() {
//                private final Button editButton = createEditButton(getTableView().getItems().get(getIndex()));
//                private final Button actionButton = createActionButton(getTableView().getItems().get(getIndex()));
//                private final Button deleteButton = createDeleteButton(getTableView().getItems().get(getIndex()));
//
//                {
//                    // Define the edit button's behavior
//                    editButton.setOnAction(event -> {
//                        Siswa siswa = getTableView().getItems().get(getIndex());
//                        // Handle edit button action here
//                        System.out.println("Edit button clicked for siswa: " + siswa);
//                    });
//
//                    // Define the action button's behavior
//                    actionButton.setOnAction(event -> {
//                        Siswa siswa = getTableView().getItems().get(getIndex());
//                        if (siswa.getStatus().equals("A")) {
//                            // Handle activate button action here
//                            System.out.println("Activate button clicked for siswa: " + siswa);
//                        } else if (siswa.getStatus().equals("D")) {
//                            // Handle deactivate button action here
//                            System.out.println("Deactivate button clicked for siswa: " + siswa);
//                        }
//                    });
//
//                    // Define the delete button's behavior
//                    deleteButton.setOnAction(event -> {
//                        Siswa siswa = getTableView().getItems().get(getIndex());
//                        // Handle delete button action here
//                        System.out.println("Delete button clicked for siswa: " + siswa);
//                    });
//                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Siswa siswa = getTableView().getItems().get(getIndex());
                        HBox buttonsContainer = new HBox(5);
                        Button infoButton = createInfoButton(siswa);
                        Button editButton = createEditButton(siswa);
                        Button deleteButton = createDeleteButton(siswa);
                        buttonsContainer.getChildren().addAll(infoButton, editButton, deleteButton);
                        setGraphic(buttonsContainer);
                    }
                }
            };
        });
        // Set any additional configuration for columns

        // Add your desired logic for the action column, such as button setup or cell rendering
        // actionColumn.setCellFactory(...);

        // Fetch data from the siswaService and populate the table
        try {
            List<Siswa> siswaList = siswaService.findAll("");
            setSiswaData(siswaList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Empty method body, as the logic is already implemented in the other initialize() method
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

    private Button createInfoButton(Siswa siswa) {
        Button button = new Button("");
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/info-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(15.0);
        button.setGraphic(imageView);
        // Customize edit button style and properties here if needed
        button.setOnAction(event -> {
            // Handle deactivate button action here
            System.out.println("Edit button clicked for siswa: " + siswa);
        });
        return button;
    }
    private Button createEditButton(Siswa siswa) {
        Button button = new Button("");
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/user-pen-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(25.0);
        button.setGraphic(imageView);
        // Customize edit button style and properties here if needed
        button.setOnAction(event -> {
            // Handle deactivate button action here
            System.out.println("Edit button clicked for siswa: " + siswa);
        });
        return button;
    }

    private Button createDeleteButton(Siswa siswa) {
        Button button = new Button("");
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/aplikasikursus/icons/trash-solid.png")));
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(15.0);
        button.setGraphic(imageView);
        // Customize delete button style and properties here if needed
        button.setOnAction(event -> {
            // Handle deactivate button action here
            System.out.println("Delete button clicked for siswa: " + siswa);
        });
        return button;
    }
    @FXML
    private void handleSearchSiswa(ActionEvent event) {
        String searchQuery = searchSiswaInput.getText();
        try {
            List<Siswa> searchResults = siswaService.findAll(searchQuery);
            setSiswaData(searchResults);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
