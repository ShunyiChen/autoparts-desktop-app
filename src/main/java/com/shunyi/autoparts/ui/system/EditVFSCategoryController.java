package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.vo.VFSCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;


/** VFS类目Controller */
public class EditVFSCategoryController {

    private Stage dialog;
    private Callback<String, String> callback;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnOk;


    @FXML
    void cancel() {
        dialog.close();
    }

    @FXML
    void ok() {
        callback.call(txtName.getText());
        dialog.close();
    }

    public void prepare(Stage dialog, Callback<String, String> callback, VFSCategory selectedVFSCategory) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(selectedVFSCategory != null) {
            txtName.setText(selectedVFSCategory.getName());
        }
    }
}
