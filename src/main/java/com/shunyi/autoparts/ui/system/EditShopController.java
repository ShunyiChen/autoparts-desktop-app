package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.model.Shop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;


/** 编辑店铺Controller */
public class EditShopController {

    private Stage dialog;
    private Callback callback;
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

    public void prepare(Stage dialog, Callback callback, Shop selectedShop) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(selectedShop != null) {
            txtName.setText(selectedShop.getName());
        }
    }
}
