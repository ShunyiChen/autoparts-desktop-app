package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.vo.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/** 编辑店铺Controller */
public class EditStoreController {
    private Stage dialog;
    private Callback callback;
    @FXML
    private TextField txtCode;
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
        Store store = new Store();
        store.setCode(txtCode.getText());
        store.setName(txtName.getText());
        callback.call(store);
        dialog.close();
    }

    public void prepare(Stage dialog, Callback callback, Store selectedStore) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(selectedStore != null) {
            txtCode.setText(selectedStore.getCode());
            txtName.setText(selectedStore.getName());
        }
    }
}
