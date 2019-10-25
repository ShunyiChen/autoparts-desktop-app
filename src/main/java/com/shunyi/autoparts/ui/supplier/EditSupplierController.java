package com.shunyi.autoparts.ui.supplier;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.Supplier;
import com.shunyi.autoparts.ui.model.SupplierCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/** 编辑供应商Controller */
public class EditSupplierController {
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContacts;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtOthers;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnContinueAdding;
    private Stage dialog;
    private Supplier updatedSupplier;
    private SupplierCategory selectedCategory;
    private Gson gson = new Gson();
    private Callback<Supplier, Object> callback;

    @FXML
    private void choose(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                    "/fxml/supplier/chooser_category.fxml"
            )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        subStage.setOnHiding( e -> {
        });
        ChooserCategoryController controller = loader.getController();
        Callback<SupplierCategory, Object> cb = new Callback<SupplierCategory, Object>() {
            @Override
            public Object call(SupplierCategory param) {
                selectedCategory = param;
                txtCategory.setText(selectedCategory.getName());
                return null;
            }
        };
        controller.prepare(subStage, selectedCategory, cb);
        subStage.setTitle("选择供应商类目");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    private void saveAndClose(ActionEvent event) {
        if(updatedSupplier == null) {
            add(true);
        } else {
            update(true);
        }
    }

    @FXML
    private void continueAdding(ActionEvent event) {
        add(false);
    }

    private boolean checkInputs() {
        if(selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("类目不能为空");
            alert.show();
            return false;
        }
        if(txtCode.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("编码不能为空");
            alert.show();
            return false;
        }
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("供应商名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }

    void update(boolean closeStage) {
        if(checkInputs()) {
            if(closeStage)
                dialog.close();
            Supplier supplier = new Supplier(txtCode.getText(), txtName.getText(), txtContacts.getText(),txtPhone.getText(),txtOthers.getText(),selectedCategory);
            supplier.setId(updatedSupplier.getId());
            callback.call(supplier);
        }
    }

    void add(boolean closeStage) {
        if(checkInputs()) {
            if(closeStage)
                dialog.close();
            Supplier supplier = new Supplier(txtCode.getText(), txtName.getText(), txtContacts.getText(),txtPhone.getText(),txtOthers.getText(),selectedCategory);
            callback.call(supplier);
        }
    }

    /**
     *
     * @param dialog
     */
    public void prepare(Stage dialog, Supplier updatedSupplier, Callback<Supplier, Object> callback) {
        this.dialog = dialog;
        this.updatedSupplier = updatedSupplier;
        this.callback = callback;
        btnSave.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(updatedSupplier != null) {
            try {
                String json = HttpClient.GET("/supplier/categories/"+updatedSupplier.getCategory().getId());
                selectedCategory = gson.fromJson(json, SupplierCategory.class);
                String name = selectedCategory.getId() == 0 ? "全部供应商":selectedCategory.getName();
                txtCategory.setText(name);
                txtCode.setText(updatedSupplier.getCode());
                txtName.setText(updatedSupplier.getName());
                txtContacts.setText(updatedSupplier.getContact());
                txtPhone.setText(updatedSupplier.getPhone());
                txtOthers.setText(updatedSupplier.getOther());
                btnContinueAdding.setVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
