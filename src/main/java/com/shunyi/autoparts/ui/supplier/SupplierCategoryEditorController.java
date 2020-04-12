package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.vo.SupplierCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @Description: 供应商分类编辑器Controller
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/12
 */
public class SupplierCategoryEditorController {
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
    private void ok() {
        SupplierCategory supplierCategory = new SupplierCategory();
        supplierCategory.setName(txtName.getText());
        callback.call(supplierCategory);
        dialog.close();
    }

    public void prepare(Stage dialog, Callback callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }
}
