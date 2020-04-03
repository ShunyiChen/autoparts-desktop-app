package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Category;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.common.vo.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/** 产品类目编辑器Controller */
public class CategoryEditorController {
    private Stage subStage;
    private Category updatedCategory;
    private Callback<Category, Object> callback;
    private Store store;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Store> comboboxStore;
    @FXML
    private void cancel() {
        subStage.close();
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
            store = HttpClient.GET("/stores/name/"+comboboxStore.getValue(), Store.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(store.getId() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个可用的门店");
            alert.show();
            return;
        }
        Category newCategory = new Category();
        newCategory.setName(txtName.getText());
        newCategory.setStore(store);
        callback.call(newCategory);
        subStage.close();
    }

    public void prepare(Stage subStage, Category updatedCategory, Callback<Category, Object> callback) {
        this.subStage = subStage;
        this.updatedCategory = updatedCategory;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        initComboBox();

        if(updatedCategory != null) {
            txtName.setText(updatedCategory.getName());
        }
    }

    private void initComboBox() {
        comboboxStore.getItems().addAll(Env.getInstance().stores());
        new AutoCompleteBox(comboboxStore);
    }
}
