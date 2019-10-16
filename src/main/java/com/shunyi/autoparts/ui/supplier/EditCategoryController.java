package com.shunyi.autoparts.ui.supplier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;


/** 编辑供应商类目名称Controller */
public class EditCategoryController {
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtName;
    private Stage dialog;
    private Callback callback;

    @FXML
    private void cancel(ActionEvent event) {
        dialog.close();
    }
    @FXML
    private void ok(ActionEvent event) {
        callback.call(txtName.getText());
        dialog.close();
    }

    public void prepare(Stage dialog, Callback callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }
}
