package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/** 产品类目编辑器Controller */
public class CategoryEditorController {
    Stage subStage;
    Category updatedCategory;
    Callback<Category, Object> callback;
    @FXML
    Button btnOk;
    @FXML
    TextField txtName;

    @FXML
    void cancel(ActionEvent actionEvent) {
        subStage.close();
    }

    @FXML
    void ok(ActionEvent actionEvent) {
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return;
        }
        subStage.close();
        Category newCategory = new Category();
        newCategory.setName(txtName.getText());
        callback.call(newCategory);
    }

    public void prepare(Stage subStage, Category updatedCategory, Callback<Category, Object> callback) {
        this.subStage = subStage;
        this.updatedCategory = updatedCategory;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        if(updatedCategory != null) {
            txtName.setText(updatedCategory.getName());
        }
    }
}
