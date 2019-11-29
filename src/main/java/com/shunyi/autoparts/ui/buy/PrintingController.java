package com.shunyi.autoparts.ui.purchase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PrintingController {

    @FXML
    private ComboBox cBoxPrinting;
    @FXML
    private ComboBox cBoxTemplate;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnPrinting;

    private Stage dialog;

    @FXML
    public void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    public void print(ActionEvent event) {
        dialog.close();
    }

    public void prepare(Stage dialog) {
        this.dialog = dialog;
//        tabPane.setStyle("-fx-font-size: 14;");
        btnPrinting.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

    }
}
