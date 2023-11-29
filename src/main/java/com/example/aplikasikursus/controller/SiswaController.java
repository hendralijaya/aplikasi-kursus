package com.example.aplikasikursus;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import java.util.List;

public class SiswaController {
    @FXML
    private TableView<SiswaRepository> siswaTable;

    @FXML
    private Pagination pagination;

    public void setSiswaData(List<SiswaRepository> siswaList) {
        siswaTable.getItems().setAll(siswaList);
    }
}
