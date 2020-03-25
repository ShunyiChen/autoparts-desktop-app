package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.vo.Category;
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
    private Stage subStage;
    private Category updatedCategory;
    private Callback<Category, Object> callback;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtName;

    @FXML
    void cancel() {
        subStage.close();
    }

    @FXML
    void ok() {
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return;
        }
        subStage.close();
        Category newCategory = new Category();
        newCategory.setName(txtName.getText());
        newCategory.setCreator(Env.getInstance().getStringValue(Env.CURRENT_USER));
        newCategory.setDeleteFlag(Constants.DELETE_FLAG_FALSE);
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
