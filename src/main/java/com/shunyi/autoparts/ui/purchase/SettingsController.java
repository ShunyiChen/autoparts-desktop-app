package com.shunyi.autoparts.ui.buy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private TabPane tabPane;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOk;

    private Stage dialog;

    @FXML
    public void restoreTableSettings(ActionEvent event) {
    }

    @FXML
    public void restoreWindowTags(ActionEvent event) {
    }

    @FXML
    public void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    public void ok(ActionEvent event) {
        dialog.close();
    }

    public void prepare(Stage dialog) {
        this.dialog = dialog;
        tabPane.setStyle("-fx-font-size: 14;");
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

    }
}
