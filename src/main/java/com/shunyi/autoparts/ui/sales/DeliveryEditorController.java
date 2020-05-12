package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.vo.Delivery;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @description 发货方式编辑器Controller
 *
 * @author Shunyi
 * @date 2020/5/12
 */
public class DeliveryEditorController {
    private Stage dialog;

    private Callback<Delivery, String> callback;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFreightCode;
    @FXML
    private TextField txtFreightName;
    @FXML
    private Button btnOk;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(validate()) {
            Delivery invoiceType = new Delivery(Constants.ID, txtName.getText(), txtFreightCode.getText(), txtFreightName.getText());
            callback.call(invoiceType);
            dialog.close();
        }
    }

    public void initialize(Stage dialog, Callback<Delivery, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("发货方式不能为空");
            alert.show();
            return false;
        }
        return true;
    }
}
