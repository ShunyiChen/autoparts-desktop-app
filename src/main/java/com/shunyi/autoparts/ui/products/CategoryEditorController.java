package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/** 产品类目编辑器Controller */
public class CategoryEditorController {
    private Stage dialog;
    private Callback<Category, Object> callback;

    @FXML
    private Button btnOk;
    @FXML
    private TextField txtName;
    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return;
        }
        try {
            Category root = HttpClient.GET("/category/root/"+Env.getInstance().currentStore().getId(), Category.class);
            Category newCategory = new Category();
            newCategory.setName(txtName.getText());
            newCategory.setStore(root.getStore());
            callback.call(newCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.close();
    }

    public void prepare(Stage dialog, Callback<Category, Object> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

}
