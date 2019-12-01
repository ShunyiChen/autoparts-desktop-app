package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MaintenanceController {

    private MainApp application;

    @FXML
    private TabPane tabPane;


    public void prepare(MainApp application) {
        this.application = application;
        tabPane.setStyle("-fx-font-size: 14;");
    }


}
