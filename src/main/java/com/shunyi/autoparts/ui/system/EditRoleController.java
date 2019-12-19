package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.model.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;


/** 编辑角色Controller */
public class EditRoleController {
    private Stage dialog;
    private Callback<Role, String> callback;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDesc;
    @FXML
    private Button btnOk;

    @FXML
    private void ok() {
        if(validate()) {
            this.callback.call(new Role(txtName.getText(), txtDesc.getText()));
            dialog.close();
        }
    }

    /**
     *
     * @return
     */
    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请输入名称");
            alert.show();
            return false;
        }
        return true;
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    /**
     *
     * @param dialog
     * @param selectedRole
     * @param callback
     */
    public void prepare(Stage dialog, Role selectedRole, Callback<Role, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(selectedRole != null) {
            txtName.setText(selectedRole.getName());
            txtDesc.setText(selectedRole.getDescription());
            txtName.selectAll();
        }
    }
}
