package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.vo.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @description 结算方式编辑器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/19
 */
public class PaymentEditorController {
    private Stage dialog;

    private Callback<Payment, String> callback;
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
            Payment place = new Payment(Constants.ID, txtCode.getText(), txtName.getText(), txtNotes.getText());
            callback.call(place);
            dialog.close();
        }
    }

    public void initialize(Stage dialog, Callback<Payment, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("结算方式不能为空");
            alert.show();
            return false;
        }
        return true;
    }
}
