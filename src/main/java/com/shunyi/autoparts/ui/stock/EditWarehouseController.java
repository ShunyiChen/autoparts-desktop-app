package com.shunyi.autoparts.ui.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** 编辑仓库Controller */
public class EditWarehouseController {
    @FXML
    Button btnCancel;
    @FXML
    Button btnOk;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;

    private Stage subStage;

    @FXML
    private void ok(ActionEvent event) {
    }
    @FXML
    private void cancel(ActionEvent event) {
    }

    public void prepare(Stage subStage) {
        this.subStage = subStage;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }
}
