package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.vo.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @description 单位编辑器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/13
 */
public class UnitEditorController {
    private Stage dialog;

    private Callback<Unit, String> callback;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnOk;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(validate()) {
            Unit unit = new Unit(Constants.ID, txtName.getText());
            callback.call(unit);
            dialog.close();
        }
    }

    public void initialize(Stage dialog, Callback<Unit, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }
}
