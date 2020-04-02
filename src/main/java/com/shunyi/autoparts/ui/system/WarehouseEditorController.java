package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.vo.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @description 仓库编辑器
 * @author Shunyi Chen
 * @date 2020/4/2
 */
public class WarehouseEditorController {
    private Stage dialog;

    private Callback<Warehouse, String> callback;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNotes;
    @FXML
    private Button btnOk;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(validate()) {
            Warehouse warehouse = new Warehouse(Constants.ID, txtCode.getText(), txtName.getText(), txtNotes.getText(), null);
            callback.call(warehouse);
            dialog.close();
        }
    }

    public void prepare(Stage dialog, Callback<Warehouse, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

    }

    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("仓库名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }
}
